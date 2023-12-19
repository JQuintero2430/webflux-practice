package com.jorge.practice.webfluxpractice.router;

import com.jorge.practice.webfluxpractice.handler.ProductHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@Slf4j

public class ProductRouter {
    private static final String PATH = "api/v1/products";

    @Bean
    RouterFunction<ServerResponse> routes(ProductHandler handler) {
        return RouterFunctions.route()
                .GET(PATH + "/list", handler::findAll)
                .GET(PATH + "/{id}", handler::findById)
                .POST(PATH, handler::create)
                .PUT(PATH + "/{id}", handler::update)
                .DELETE(PATH + "/{id}", handler::delete)
                .build();
    }

}
