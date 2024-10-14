package com.enoca.ecommerce.dto;

import com.enoca.ecommerce.entity.Customer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CustomerDto(

        Long id,
        @NotNull
        String name,
        @Email
        @NotNull
        String email
) {

    static public CustomerDto of(Customer customer) {
        return new CustomerDto(
                customer.getId(),
                customer.getName(),
                customer.getEmail()
        );
    }

    static public List<CustomerDto> of(List<Customer> customers) {
        return customers.stream().map(CustomerDto::of).toList();
    }

    static public Customer toEntity(CustomerDto customerDto) {
        return Customer.builder()
                .id(customerDto.id())
                .name(customerDto.name())
                .email(customerDto.email())
                .deleted(false)
                .build();
    }

    static public Customer toEntityForCreate(CustomerDto customerDto) {
        return Customer.builder()
                .name(customerDto.name())
                .email(customerDto.email())
                .deleted(false)
                .build();
    }
}
