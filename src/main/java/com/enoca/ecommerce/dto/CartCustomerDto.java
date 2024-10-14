package com.enoca.ecommerce.dto;

import com.enoca.ecommerce.entity.Cart;

import java.math.BigDecimal;
import java.util.List;

public record CartCustomerDto(
        Long id,
        BigDecimal totalPrice,

        CustomerDto customer,

        List<ProductHolderDto> productHolder
) {
    static public CartCustomerDto of(Cart cart) {
        return new CartCustomerDto(
                cart.getId(),
                cart.getTotalPrice(),
                CustomerDto.of(cart.getCustomer()),
                ProductHolderDto.of(cart.getProducts())
        );
    }

    static public List<CartCustomerDto> of(List<Cart> carts) {
        return carts.stream().map(CartCustomerDto::of).toList();
    }

    static public Cart toEntity(CartCustomerDto cartDto) {
        return Cart.builder()
                .id(cartDto.id())
                .totalPrice(cartDto.totalPrice())
                .customer(CustomerDto.toEntity(cartDto.customer()))
                .products(ProductHolderDto.toEntities(cartDto.productHolder()))
                .build();
    }

    static public List<Cart> toEntities(List<CartCustomerDto> cartDtos) {
        return cartDtos.stream().map(CartCustomerDto::toEntity).toList();
    }




}
