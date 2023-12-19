package com.jorge.practice.webfluxpractice.service.impl;

import com.jorge.practice.webfluxpractice.entity.Product;
import com.jorge.practice.webfluxpractice.repository.IProductRepository;
import com.jorge.practice.webfluxpractice.service.IProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final IProductRepository productRepository;

    @Override
    public Mono<Product> create(Product product) {
        return productRepository.findByName(product.getName())
                .hasElement()
                .flatMap(exists -> exists ? Mono.error(new RuntimeException("Product name is already used"))
                        : productRepository.save(product));
    }

    @Override
    public Flux<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Mono<Product> findById(Long id) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Product not found")));
    }

    @Override
    public Mono<Product> update(long id, Product product) {
        return productRepository.findById(id)
                .flatMap(p -> productRepository.findByIdNotAndName(id, product.getName())
                        .hasElement()
                        .flatMap(exists -> exists ? Mono.error(new RuntimeException("Product name is already used"))
                                : Mono.just(p)))
                .switchIfEmpty(Mono.error(new RuntimeException("Product not found")))
                .flatMap(p -> {
                    p.setName(product.getName());
                    p.setPrice(product.getPrice());
                    return productRepository.save(p);
                });
    }

    @Override
    public Mono<String> delete(Long id) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Product not found")))
                .flatMap(productRepository::delete)
                .then(Mono.just("Product with id "+ id + " was deleted successfully"));
    }
}
