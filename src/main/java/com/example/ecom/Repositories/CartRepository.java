package com.example.ecom.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecom.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserId(Long userId);
}