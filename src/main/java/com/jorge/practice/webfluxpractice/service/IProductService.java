package com.jorge.practice.webfluxpractice.service;

import com.jorge.practice.webfluxpractice.entity.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProductService {
    public Mono<Product> create(Product product);
    public Flux<Product> findAll();
    public Mono<Product> findById(Long id);
    public Mono<Product> update(long id, Product product);
    public Mono<String> delete(Long id);

}
