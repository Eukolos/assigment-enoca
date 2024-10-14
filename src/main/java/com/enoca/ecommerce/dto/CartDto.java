package com.enoca.ecommerce.dto;

import com.enoca.ecommerce.entity.Cart;
import com.enoca.ecommerce.entity.ProductHolder;

import java.math.BigDecimal;
import java.util.List;

public record CartDto(
        Long id,
        BigDecimal totalPrice,

        List<ProductHolderDto> productHolder
) {
    static public CartDto of(Cart cart) {
        return new CartDto(
                cart.getId(),
                cart.getTotalPrice(),
                ProductHolderDto.of(cart.getProducts())
        );
    }

    static public List<CartDto> of(List<Cart> carts) {
        return carts.stream().map(CartDto::of).toList();
    }

    static public Cart toEntity(CartDto cartDto) {
        return Cart.builder()
                .id(cartDto.id())
                .totalPrice(cartDto.totalPrice())
                .products(ProductHolderDto.toEntities(cartDto.productHolder()))
                .build();
    }


}
