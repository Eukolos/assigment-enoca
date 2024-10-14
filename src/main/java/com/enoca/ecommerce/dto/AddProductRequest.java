package com.enoca.ecommerce.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AddProductRequest (
        @NotNull
        Long productId,
        @Min(1)
        Integer quantity
){
}
