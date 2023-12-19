package com.jorge.practice.webfluxpractice.repository;

import com.jorge.practice.webfluxpractice.entity.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends ReactiveCrudRepository<Product, Long> {
}
