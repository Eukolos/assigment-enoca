package com.enoca.ecommerce.dto;

import com.enoca.ecommerce.entity.ProductHolder;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record ProductHolderDto(
        Long id,
        Integer quantity,
        BigDecimal price,
        String productName,
        String productDescription
) {
    static public ProductHolderDto of(ProductHolder productHolder) {
        return new ProductHolderDto(
                productHolder.getId(),
                productHolder.getQuantity(),
                productHolder.getPrice(),
                productHolder.getProduct().getName(),
                productHolder.getProduct().getDescription()
        );
    }

    static public List<ProductHolderDto> of(List<ProductHolder> productHolders) {
        return productHolders.stream().map(ProductHolderDto::of).toList();
    }



    static public ProductHolder toEntity(ProductHolderDto productHolderDto) {
        return ProductHolder.builder()
                .id(productHolderDto.id())
                .quantity(productHolderDto.quantity())
                .price(productHolderDto.price())
                .build();
    }

    static public List<ProductHolder> toEntities(List<ProductHolderDto> productHolderDtos) {
        return productHolderDtos.stream().map(ProductHolderDto::toEntity).toList();
    }



}
