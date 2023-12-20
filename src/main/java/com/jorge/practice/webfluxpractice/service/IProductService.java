package com.jorge.practice.webfluxpractice.service;

import com.jorge.practice.webfluxpractice.entity.Product;
import com.jorge.practice.webfluxpractice.entity.dto.ProductDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProductService {
    public Mono<Product> create(ProductDto productDto);
    public Flux<ProductDto> findAll();
    public Mono<ProductDto> findById(Long id);
    public Mono<Product> update(long id, ProductDto productDto);
    public Mono<String> delete(Long id);

}
