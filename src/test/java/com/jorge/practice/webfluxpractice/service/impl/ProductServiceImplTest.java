package com.jorge.practice.webfluxpractice.service.impl;

import com.jorge.practice.webfluxpractice.entity.Product;
import com.jorge.practice.webfluxpractice.entity.dto.ProductDto;
import com.jorge.practice.webfluxpractice.exception.ProductListEmptyException;
import com.jorge.practice.webfluxpractice.exception.ProductNameUsedException;
import com.jorge.practice.webfluxpractice.exception.ProductNotFoundException;
import com.jorge.practice.webfluxpractice.mapper.ProductMapper;
import com.jorge.practice.webfluxpractice.repository.IProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private IProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    private final Product product = new Product(1L, "Product 1", 100.00);
    private final ProductDto productDto = new ProductDto(1L, "Product 1", 100.00);

    @Test
    void create() {
        when(productMapper.productDtoToProduct(any(ProductDto.class))).thenReturn(product);
        when(productRepository.findByName(any(String.class))).thenReturn(Mono.empty());
        when(productRepository.save(any(Product.class))).thenReturn(Mono.just(product));

        StepVerifier.create(productService.create(productDto))
                .expectNext(product)
                .verifyComplete();

        when(productRepository.findByName(any(String.class))).thenReturn(Mono.just(product));

        StepVerifier.create(productService.create(productDto))
                .expectErrorMatches(throwable -> throwable instanceof ProductNameUsedException
                        && throwable.getMessage().equals("Product name is already used"))
                .verify();
    }

    @Test
    void findAll() {
        when(productRepository.findAll()).thenReturn(Flux.empty());
        StepVerifier.create(productService.findAll())
                .expectErrorMatches(throwable -> throwable instanceof ProductListEmptyException
                        && throwable.getMessage().equals("Products list is empty"))
                .verify();

        when(productRepository.findAll()).thenReturn(Flux.just(product));
        when(productMapper.productToProductDto(product)).thenReturn(productDto);

        StepVerifier.create(productService.findAll())
                .expectNext(productDto)
                .verifyComplete();
    }

    @Test
    void findById() {
        when(productRepository.findById(any(Long.class))).thenReturn(Mono.just(product));
        when(productMapper.productToProductDto(product)).thenReturn(productDto);

        StepVerifier.create(productService.findById(1L))
                .expectNext(productDto)
                .verifyComplete();

        when(productRepository.findById(any(Long.class))).thenReturn(Mono.empty());
        StepVerifier.create(productService.findById(1L))
                .expectErrorMatches(throwable -> throwable instanceof ProductNotFoundException
                        && throwable.getMessage().equals("Product not found"))
                .verify();
    }

    @Test
    void update() {
        ProductDto productDto2 = ProductDto.builder().name("Product 3").price(234.23).build();
        when(productRepository.findById(any(Long.class))).thenReturn(Mono.just(product));
        when(productRepository.findByIdNotAndName(any(Long.class), any(String.class))).thenReturn(Mono.empty());
        when(productRepository.save(any(Product.class))).thenReturn(Mono.just(product));

        StepVerifier.create(productService.update(1L, productDto2))
                .expectNext(product)
                .verifyComplete();

        when(productRepository.findById(any(Long.class))).thenReturn(Mono.empty());
        StepVerifier.create(productService.update(1L, productDto2))
                .expectErrorMatches(throwable -> throwable instanceof ProductNotFoundException
                        && throwable.getMessage().equals("Product not found"))
                .verify();
    }

    @Test
    void delete() {
        when(productRepository.findById(any(Long.class))).thenReturn(Mono.just(product));
        when(productRepository.delete(any(Product.class))).thenReturn(Mono.empty());

        StepVerifier.create(productService.delete(1L))
                .expectNext("Product with id 1 was deleted successfully")
                .verifyComplete();

        when(productRepository.findById(any(Long.class))).thenReturn(Mono.empty());
        StepVerifier.create(productService.delete(1L))
                .expectErrorMatches(throwable -> throwable instanceof ProductNotFoundException
                        && throwable.getMessage().equals("Product not found"))
                .verify();
    }
}