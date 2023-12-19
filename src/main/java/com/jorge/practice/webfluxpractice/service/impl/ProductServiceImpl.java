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
        return productRepository.save(product);
    }

    @Override
    public Flux<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Mono<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Mono<Void> update(long id, Product product) {
        return productRepository.save(new Product(id, product.getName(), product.getPrice())).then();
    }

    @Override
    public Mono<Void> delete(Long id) {
        return productRepository.deleteById(id);
    }
}
