package com.jorge.practice.webfluxpractice.repository;

import com.jorge.practice.webfluxpractice.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class IProductRepositoryTest {

    @MockBean
    private IProductRepository iProductRepository;
    private Product productMock;
    @BeforeEach
    void setUp() {
        productMock = new Product(1L,"Product 1", 100.00);
    }
    @Test
    void findByName() {
        when(iProductRepository.findByName("Product 1")).thenReturn(Mono.just(productMock));

        Mono<Product> productMono = iProductRepository.findByName("Product 1");


        assertNotNull(productMono.block());
        assertEquals(1L, Objects.requireNonNull(productMono.block()).getId());
        assertEquals(100.00, Objects.requireNonNull(productMono.block()).getPrice());
    }

    @Test
    void findByIdNotAndName() {
        when(iProductRepository.findByIdNotAndName(1L, "Product 1")).thenReturn(Mono.just(productMock));

        assertNotNull(iProductRepository.findByIdNotAndName(1L, "Product 1").block());
        assertEquals(1L, Objects.requireNonNull(iProductRepository.findByIdNotAndName(1L, "Product 1").block()).getId());
        assertEquals("Product 1", Objects.requireNonNull(iProductRepository.findByIdNotAndName(1L, "Product 1").block()).getName());
    }
}