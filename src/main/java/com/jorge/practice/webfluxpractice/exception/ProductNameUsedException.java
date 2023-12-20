package com.jorge.practice.webfluxpractice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ProductNameUsedException extends RuntimeException{
    private final HttpStatus status;

    public ProductNameUsedException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

}
