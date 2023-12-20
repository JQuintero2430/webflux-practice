package com.jorge.practice.webfluxpractice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ProductListEmptyException extends RuntimeException{
    private final HttpStatus status;
    public ProductListEmptyException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}
