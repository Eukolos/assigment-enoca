package com.enoca.ecommerce.controller;


import com.enoca.ecommerce.aop.InMemoryLogger;
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
    private final InMemoryLogger log;

    @GetMapping("/{id}")
    public CustomerDto getCustomer(@PathVariable Long id) {
        CustomerDto customer = service.getCustomer(id);
        log.log("CustomerController -> getCustomer -> customer: " + customer);
        return customer;
    }

    @GetMapping("/cart/{id}")
    public CustomerCartDto getCustomerCart(@PathVariable Long id) {
        CustomerCartDto customerCart = service.getCustomerCart(id);
        log.log("CustomerController -> getCustomerCart -> customerCart: " + customerCart);
        return customerCart;
    }

    @GetMapping
    public List<CustomerDto> getCustomers() {
        List<CustomerDto> customers = service.getCustomers();
        log.log("CustomerController -> getCustomers -> customers: " + customers);
        return customers;
    }

    @GetMapping("/cart")
    public List<CustomerCartDto> getCustomerWithCart() {
        List<CustomerCartDto> customerWithCart = service.getCustomerWithCart();
        log.log("CustomerController -> getCustomerWithCart -> customerWithCart: " + customerWithCart);
        return customerWithCart;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDto createCustomer(@RequestBody CustomerDto customerDto) {
        CustomerDto customer = service.createCustomer(customerDto);
        log.log("CustomerController -> createCustomer -> customer: " + customer);
        return customer;
    }



    @PutMapping
    public CustomerDto updateCustomer(@RequestBody CustomerDto customerDto) {
        CustomerDto customerDto1 = service.updateCustomer(customerDto);
        log.log("CustomerController -> updateCustomer -> customerDto: " + customerDto1);
        return customerDto1;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable Long id) {
        service.deleteCustomer(id);
        log.log("CustomerController -> deleteCustomer -> customer deleted");
    }
}
