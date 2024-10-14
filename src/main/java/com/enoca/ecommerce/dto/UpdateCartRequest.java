package com.enoca.ecommerce.dto;


public record UpdateCartRequest(
        Long productId,
        Integer quantity

) {



}
