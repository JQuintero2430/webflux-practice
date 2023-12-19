package com.jorge.practice.webfluxpractice.handler;

import com.jorge.practice.webfluxpractice.entity.Product;
import com.jorge.practice.webfluxpractice.service.IProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProductHandler {
    private final IProductService productService;

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(productService.findAll(), Product.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(productService.findById(Long.valueOf(request.pathVariable("id"))), Product.class);
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(Product.class)
                .flatMap(p -> ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(productService.create(p), Product.class));
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        return request.bodyToMono(Product.class)
                .flatMap(p -> ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(productService.update(Long.parseLong(request.pathVariable("id")), p), Product.class));
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(productService.delete(Long.parseLong(request.pathVariable("id"))), Product.class);
    }
}
