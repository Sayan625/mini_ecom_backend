package com.example.ecom.Repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ecom.model.Order;

public interface OrderRepository  extends JpaRepository<Order, Long> {
    
    List<Order> findByUserId(Long userId);
}