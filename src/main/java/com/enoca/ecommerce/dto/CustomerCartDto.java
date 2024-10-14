package com.enoca.ecommerce.dto;

import com.enoca.ecommerce.entity.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public record CustomerCartDto(
        Long id,
        String name,
        String email,
        CartDto cart
) {

    static public CustomerCartDto of(Customer customer) {
        return new CustomerCartDto(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                CartDto.of(customer.getCart()
                ));
    }

    static public List<CustomerCartDto> of(List<Customer> customers) {
        return customers.stream().map(CustomerCartDto::of).toList();
    }

    static public Customer toEntity(CustomerCartDto customerCartDto) {
        return Customer.builder()
                .id(customerCartDto.id())
                .name(customerCartDto.name())
                .email(customerCartDto.email())
                .cart(CartDto.toEntity(customerCartDto.cart()))
                .build();
    }


}
