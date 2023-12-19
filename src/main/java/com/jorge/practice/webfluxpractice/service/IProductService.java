package com.jorge.practice.webfluxpractice.service;

import com.jorge.practice.webfluxpractice.entity.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProductService {
    public Mono<Product> create(Product product);
    public Flux<Product> findAll();
    public Mono<Product> findById(Long id);
    public Mono<Void> update(long id, Product product);
    public Mono<Void> delete(Long id);

}
