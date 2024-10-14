package com.enoca.ecommerce.service;

import com.enoca.ecommerce.dto.CustomerCartDto;
import com.enoca.ecommerce.dto.CustomerDto;
import com.enoca.ecommerce.entity.Customer;
import com.enoca.ecommerce.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository repository;
    private final CartService cartService;

    public CustomerDto getCustomer(Long id) {
        Customer customer = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + id));
        return CustomerDto.of(customer);
    }

    public CustomerCartDto getCustomerCart(Long id) {
        Customer customer = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + id));
        return CustomerCartDto.of(customer);
    }
    public List<CustomerDto> getCustomers() {
        return CustomerDto.of(repository.findAll());
    }
    public List<CustomerCartDto> getCustomerWithCart() {
        return CustomerCartDto.of(repository.findAll());
    }

    public CustomerDto createCustomer(CustomerDto customerDto) {
        Customer customer = repository.save(CustomerDto.toEntityForCreate(customerDto));
        cartService.createCartForCustomer(customer);
        return CustomerDto.of(customer);
    }

    public CustomerDto updateCustomer(CustomerDto customerDto) {
        Customer customer = repository.findById(customerDto.id()).orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + customerDto.id()));
        Customer updatedCustomer = customer.toBuilder()
                .name(customerDto.name())
                .email(customerDto.email())
                .build();
        return CustomerDto.of(repository.save(updatedCustomer));
    }

    public void deleteCustomer(Long id) {
//        Customer customer = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + id));
//        repository.save(customer);

        repository.softDeleteById(id);
    }
}
