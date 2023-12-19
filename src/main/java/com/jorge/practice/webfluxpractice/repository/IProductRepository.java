package com.jorge.practice.webfluxpractice.repository;

import com.jorge.practice.webfluxpractice.entity.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface IProductRepository extends ReactiveCrudRepository<Product, Long> {
    Mono<Product> findByName(String name);
    Mono<Product> findByIdNotAndName(Long id, String name);
}
