package com.jorge.practice.webfluxpractice.service.impl;

import com.jorge.practice.webfluxpractice.entity.Product;
import com.jorge.practice.webfluxpractice.exception.ProductListEmptyException;
import com.jorge.practice.webfluxpractice.exception.ProductNameUsedException;
import com.jorge.practice.webfluxpractice.exception.ProductNotFoundException;
import com.jorge.practice.webfluxpractice.repository.IProductRepository;
import com.jorge.practice.webfluxpractice.service.IProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final IProductRepository productRepository;
    private final String NAME_ALREADY_USED = "Product name is already used";
    private final String PRODUCT_NOT_FOUND = "Product not found";
    private final String PRODUCT_LIST_EMPTY = "Products list is empty";

    @Override
    public Mono<Product> create(Product product) {
        return productRepository.findByName(product.getName())
                .hasElement()
                .flatMap(exists -> exists ? Mono.error(new ProductNameUsedException(HttpStatus.BAD_REQUEST, NAME_ALREADY_USED))
                        : productRepository.save(product));
    }

    @Override
    public Flux<Product> findAll() {
        return productRepository.findAll()
                .switchIfEmpty(Mono.error(new ProductListEmptyException(HttpStatus.BAD_REQUEST, PRODUCT_LIST_EMPTY)));
    }

    @Override
    public Mono<Product> findById(Long id) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new ProductNotFoundException(HttpStatus.NOT_FOUND, PRODUCT_NOT_FOUND)));
    }

    @Override
    public Mono<Product> update(long id, Product product) {
        return productRepository.findById(id)
                .flatMap(p -> productRepository.findByIdNotAndName(id, product.getName())
                        .hasElement()
                        .flatMap(exists -> exists ? Mono.error(new ProductNameUsedException(HttpStatus.BAD_REQUEST, NAME_ALREADY_USED))
                                : Mono.just(p)))
                .switchIfEmpty(Mono.error(new ProductNotFoundException(HttpStatus.NOT_FOUND, PRODUCT_NOT_FOUND)))
                .flatMap(p -> {
                    p.setName(product.getName());
                    p.setPrice(product.getPrice());
                    return productRepository.save(p);
                });
    }

    @Override
    public Mono<String> delete(Long id) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new ProductNotFoundException(HttpStatus.NOT_FOUND, PRODUCT_NOT_FOUND)))
                .flatMap(productRepository::delete)
                .then(Mono.just("Product with id "+ id + " was deleted successfully"));
    }
}
