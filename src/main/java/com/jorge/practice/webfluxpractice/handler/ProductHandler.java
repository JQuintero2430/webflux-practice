package com.jorge.practice.webfluxpractice.handler;

import com.jorge.practice.webfluxpractice.entity.dto.ProductDto;
import com.jorge.practice.webfluxpractice.exception.ProductException;
import com.jorge.practice.webfluxpractice.service.IProductService;
import com.jorge.practice.webfluxpractice.validation.ObjectValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProductHandler {
    private final IProductService productService;
    private final ObjectValidator objectValidator;

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return productService.findAll()
                .collectList()
                .flatMap(products -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(products))
                .onErrorResume(ProductException.class, e -> ServerResponse
                        .status(e.getStatus())
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(Map.of("error", true,
                                "status", e.getStatus().value(),
                                "message", e.getMessage())));
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        return productService.findById(Long.valueOf(request.pathVariable("id")))
                .flatMap(p -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(p))
                .onErrorResume(ProductException.class, e -> ServerResponse
                        .status(e.getStatus())
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(Map.of("error", true,
                                "status", e.getStatus().value(),
                                "message", e.getMessage())));
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(ProductDto.class)
                .doOnNext(objectValidator::validate)
                .flatMap(productService::create)
                .flatMap(p -> ServerResponse.created(request.uriBuilder().path("/{id}").build(p.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(p))
                .onErrorResume(ProductException.class, e -> ServerResponse
                        .status(e.getStatus())
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(Map.of("error", true,
                                "status", e.getStatus().value(),
                                "message", e.getMessage())));
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        return request.bodyToMono(ProductDto.class)
                .doOnNext(objectValidator::validate)
                .flatMap(product -> productService
                        .update(Long.parseLong(request.pathVariable("id")), product))
                .flatMap(p -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(p))
                .onErrorResume(ProductException.class, e -> ServerResponse
                        .status(e.getStatus())
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(Map.of("error", true,
                                "status", e.getStatus().value(),
                                "message", e.getMessage())))
                .onErrorResume(ProductException.class, e -> ServerResponse
                        .status(e.getStatus())
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(Map.of("error", true,
                                "status", e.getStatus().value(),
                                "message", e.getMessage())));
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        return productService.delete(Long.valueOf(request.pathVariable("id")))
                .flatMap(p -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(p))
                .onErrorResume(ProductException.class, e -> ServerResponse
                        .status(e.getStatus())
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(Map.of("error", true,
                                "status", e.getStatus().value(),
                                "message", e.getMessage())));
    }
}
