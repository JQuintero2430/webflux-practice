package com.jorge.practice.webfluxpractice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public class ProductException extends RuntimeException {
    private final HttpStatus status;

    public ProductException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}
