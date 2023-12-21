package com.jorge.practice.webfluxpractice.entity.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
    @Min(value = 1, message = "Product id must be greater than 0")
    private Long id;
    @NotBlank(message = "Product name is required")
    private String name;
    @Min(value = 1, message = "Product price must be greater than 0")
    private Double price;
}
