package com.enoca.ecommerce.controller;


import com.enoca.ecommerce.dto.CustomerCartDto;
import com.enoca.ecommerce.dto.CustomerDto;
import com.enoca.ecommerce.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService service;

    @GetMapping("/{id}")
    public CustomerDto getCustomer(@PathVariable Long id) {
        return service.getCustomer(id);
    }

    @GetMapping("/cart/{id}")
    public CustomerCartDto getCustomerCart(@PathVariable Long id) {
        return service.getCustomerCart(id);
    }

    @GetMapping
    public List<CustomerDto> getCustomers() {
        return service.getCustomers();
    }

    @GetMapping("/cart")
    public List<CustomerCartDto> getCustomerWithCart() {
        return service.getCustomerWithCart();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDto createCustomer(@RequestBody CustomerDto customerDto) {
        return service.createCustomer(customerDto);
    }



    @PutMapping
    public CustomerDto updateCustomer(@RequestBody CustomerDto customerDto) {
        return service.updateCustomer(customerDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable Long id) {
        service.deleteCustomer(id);
    }
}
