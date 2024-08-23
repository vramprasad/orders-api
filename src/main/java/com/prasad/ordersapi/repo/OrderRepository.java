package com.prasad.ordersapi.repo;


import com.prasad.ordersapi.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
