package com.jorge.practice.webfluxpractice.handler;

import com.jorge.practice.webfluxpractice.entity.Product;
import com.jorge.practice.webfluxpractice.entity.dto.ProductDto;
import com.jorge.practice.webfluxpractice.service.IProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductHandlerTest {
    @Mock
    private IProductService productService;

    @Mock
    private ServerRequest serverRequest;

    @InjectMocks
    private ProductHandler productHandler;

    private final Product testProduct = new Product(1L, "Test product", 100.0);
    private final ProductDto testProductDto = new ProductDto(1L, "Test product", 100.0);

    @Test
    void findAll() {
        when(productService.findAll()).thenReturn(Flux.just(testProductDto));

        Mono<ServerResponse> response = productHandler.findAll(serverRequest);

        StepVerifier.create(response)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is2xxSuccessful())
                .verifyComplete();
    }

    @Test
    void findById() {
        when(serverRequest.pathVariable("id")).thenReturn("1");
        when(productService.findById(anyLong())).thenReturn(Mono.just(testProductDto));

        Mono<ServerResponse> response = productHandler.findById(serverRequest);

        StepVerifier.create(response)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is2xxSuccessful())
                .verifyComplete();

    }

    @Test
    void update() {
        when(serverRequest.pathVariable("id")).thenReturn("1");
        when(serverRequest.bodyToMono(ProductDto.class)).thenReturn(Mono.just(testProductDto));
        when(productService.update(anyLong(), any(ProductDto.class))).thenReturn(Mono.just(testProduct));

        Mono<ServerResponse> response = productHandler.update(serverRequest);

        StepVerifier.create(response)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is2xxSuccessful())
                .verifyComplete();
    }

    @Test
    void delete() {
        when(serverRequest.pathVariable("id")).thenReturn("1");
        when(productService.delete(anyLong())).thenReturn(Mono.just("Product with id 1 was deleted successfully"));

        Mono<ServerResponse> response = productHandler.delete(serverRequest);

        StepVerifier.create(response)
                .expectNextMatches(serverResponse -> serverResponse.statusCode().is2xxSuccessful())
                .verifyComplete();
    }
}