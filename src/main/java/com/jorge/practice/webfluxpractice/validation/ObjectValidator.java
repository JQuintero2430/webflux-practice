package com.jorge.practice.webfluxpractice.validation;

import com.jorge.practice.webfluxpractice.exception.ProductException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;


import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class ObjectValidator {

    private final Validator validator;
    @SneakyThrows
    public <T> T validate (T object) {
        Set<ConstraintViolation<T>> errors = validator.validate(object);
        if (!errors.isEmpty()) {
            String message = errors.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(", "));
            throw new ProductException(HttpStatus.BAD_REQUEST, message);
        }
        return object;
    }
}
