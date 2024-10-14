package com.enoca.ecommerce.dto;

import com.enoca.ecommerce.entity.Product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record ProductDto(
        Long id,
        @NotNull
        String name,

        String description,
        @Min(0)
        @NotNull
        Double price,
        @Min(0)
        @NotNull
        Integer stock
) {

    static public ProductDto of(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice().doubleValue(),
                product.getStock()
        );
    }

    static public List<ProductDto> of(List<Product> products) {
        return products.stream().map(ProductDto::of).toList();
    }

    static public Product toEntity(ProductDto productDto) {
        return Product.builder()
                .id(productDto.id())
                .name(productDto.name())
                .description(productDto.description())
                .price(BigDecimal.valueOf(productDto.price()))
                .stock(productDto.stock())
                .build();
    }

    static public List<Product> toEntities(List<ProductDto> productDtos) {
        return productDtos.stream().map(ProductDto::toEntity).toList();
    }
}
