package com.jorge.practice.webfluxpractice.mapper;

import com.jorge.practice.webfluxpractice.entity.Product;
import com.jorge.practice.webfluxpractice.entity.dto.ProductDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper MAPPER = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "id", source = "id")
    ProductDto productToProductDto(Product product);
    @InheritInverseConfiguration
    Product productDtoToProduct(ProductDto productDto);
}
