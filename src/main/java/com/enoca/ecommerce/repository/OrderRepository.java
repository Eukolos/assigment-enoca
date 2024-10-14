package com.enoca.ecommerce.repository;

import com.enoca.ecommerce.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findOrderByOrderCode(String orderCode);

    Optional<List<Order>> findOrdersByCustomerId(Long customerId);
}
