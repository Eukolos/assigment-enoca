package com.enoca.ecommerce.dto;

import com.enoca.ecommerce.entity.Cart;
import com.enoca.ecommerce.entity.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderDto(
        Long id,
        String orderCode,
        LocalDateTime orderDate,
        CustomerDto customer,

        List<ProductHolderDto> products,

        BigDecimal totalAmount
) {
    static public OrderDto of(Order order) {
        return new OrderDto(
                order.getId(),
                order.getOrderCode(),
                order.getOrderDate(),
                CustomerDto.of(order.getCustomer()),
                ProductHolderDto.of(order.getProducts()),
                order.getTotalAmount()
        );
    }

    static public List<OrderDto> of(List<Order> orders) {
        return orders.stream().map(OrderDto::of).toList();
    }

    public static Order toEntity(OrderDto orderDto) {
        return Order.builder()
                .orderCode(orderDto.orderCode())
                .orderDate(orderDto.orderDate())
                .customer(CustomerDto.toEntity(orderDto.customer()))
                .products(ProductHolderDto.toEntities(orderDto.products()))
                .totalAmount(orderDto.totalAmount())
                .build();
    }

    static public List<Order> toEntities(List<OrderDto> orderDtos) {
        return orderDtos.stream().map(OrderDto::toEntity).toList();
    }

    static public Order toEntity(Cart cart) {
        return Order.builder()
                .orderCode("ORD" + System.currentTimeMillis() + cart.getId().hashCode())
                .orderDate(LocalDateTime.now())
                .customer(cart.getCustomer())
                .products(cart.getProducts())
                .totalAmount(cart.getTotalPrice())
                .build();
    }
}
