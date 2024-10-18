package com.enoca.ecommerce.controller;

import com.enoca.ecommerce.aop.InMemoryLogger;
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
    private final InMemoryLogger log;

    @PostMapping("/placeOrder/{cartId}")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto placeOrder(@PathVariable Long cartId) {
        OrderDto orderDto = service.placeOrder(cartId);
        log.log("OrderController -> placeOrder -> orderDto: " + orderDto);
        return orderDto;
    }
    @GetMapping
    public List<OrderDto> getOrders() {
        List<OrderDto> orders = service.getOrders();
        log.log("OrderController -> getOrders -> orders: " + orders);
        return orders;
    }

    @GetMapping("/{id}")
    public OrderDto getOrder(Long id) {
        OrderDto order = service.getOrder(id);
        log.log("OrderController -> getOrder -> order: " + order);
        return order;
    }

    @GetMapping("/orderCode/{orderCode}")
    public OrderDto getOrderByOrderCode( @PathVariable String orderCode) {
        OrderDto orderByOrderCode = service.getOrderByOrderCode(orderCode);
        log.log("OrderController -> getOrderByOrderCode -> orderByOrderCode: " + orderByOrderCode);
        return orderByOrderCode;
    }

    @GetMapping("/customer/{customerId}")
    public List<OrderDto> getOrdersByCustomerId(@PathVariable Long customerId) {
        List<OrderDto> ordersByCustomerId = service.getOrdersByCustomerId(customerId);
        log.log("OrderController -> getOrdersByCustomerId -> ordersByCustomerId: " + ordersByCustomerId);
        return ordersByCustomerId;
    }


}

