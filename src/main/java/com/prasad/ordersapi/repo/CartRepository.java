package com.prasad.ordersapi.repo;


import com.prasad.ordersapi.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<CartItem, Long> {
}
