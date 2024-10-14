package com.enoca.ecommerce.controller;

import com.enoca.ecommerce.dto.OrderDto;
import com.enoca.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService service;

    @PostMapping("/placeOrder/{cartId}")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto placeOrder(@PathVariable Long cartId) {
        return service.placeOrder(cartId);
    }
    @GetMapping
    public List<OrderDto> getOrders() {
        return service.getOrders();
    }

    @GetMapping("/{id}")
    public OrderDto getOrder(Long id) {
        return service.getOrder(id);
    }

    @GetMapping("/orderCode/{orderCode}")
    public OrderDto getOrderByOrderCode( @PathVariable String orderCode) {
        return service.getOrderByOrderCode(orderCode);
    }

    @GetMapping("/customer/{customerId}")
    public List<OrderDto> getOrdersByCustomerId(@PathVariable Long customerId) {
        return service.getOrdersByCustomerId(customerId);
    }


}

