package com.enoca.ecommerce.service;

import com.enoca.ecommerce.dto.OrderDto;
import com.enoca.ecommerce.entity.Cart;
import com.enoca.ecommerce.entity.Order;
import com.enoca.ecommerce.entity.ProductHolder;
import com.enoca.ecommerce.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository repository;
    private final CartService cartService;
    private final ProductHolderService productHolderService;

    public OrderDto placeOrder(Long cartId) {
        Cart cart = cartService.getCart(cartId);
        if (cart.getProducts().isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }
        Order order = repository.save(Order.builder()
                .orderCode("ORD" + System.currentTimeMillis() + cart.getId().hashCode())
                .orderDate(LocalDateTime.now())
                .customer(cart.getCustomer())
                .totalAmount(cart.getTotalPrice())
                .build());

        productHolderService.cartToOrder(cart, order);
        cartService.totalReset(cart.getId());


        return OrderDto.of(repository.findById(order.getId()).orElseThrow(
                () -> new IllegalArgumentException("Order not found")
        ));
    }

    public OrderDto getOrder(Long id) {
        Order order =  repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Order not found"));
        return OrderDto.of(order);
    }

    public OrderDto getOrderByOrderCode(String orderCode) {
        Order order = repository.findOrderByOrderCode(orderCode).orElseThrow(() -> new IllegalArgumentException("Order not found"));
        return OrderDto.of(order);
    }

    public List<OrderDto> getOrdersByCustomerId(Long customerId) {
        return OrderDto.of(repository.findOrdersByCustomerId(customerId).orElse(List.of()));
    }

    public List<OrderDto> getOrders() {
        return OrderDto.of(repository.findAll());
    }





}
