package com.example.ecom.controllers;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecom.model.Order;
import com.example.ecom.model.User;
import com.example.ecom.services.OrderService;

@RestController
@RequestMapping("/api")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping("/user/orders/checkout/{userId}")
    public Order place(@PathVariable Long userId) {
        User authUser = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        if (!(authUser.getId().equals(userId))) {
            throw new RuntimeException("Unauthorized access");
        }
        return service.placeOrder(userId);
    }

    @GetMapping("/admin/orders/all")
    public List<Order> all() {
        User authUser = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        if (!(authUser.getRole().equals("ADMIN"))) {
            throw new RuntimeException("Unauthorized access");
        }
        return service.getAll();
    }

    @GetMapping("/user/orders/{userId}")
    public List<Order> getUserOrders(@PathVariable Long userId) {
        User authUser = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        if (!(authUser.getRole().equals("ADMIN"))) {
            throw new RuntimeException("Unauthorized access");
        }
        return service.getByUserId(userId);
    }
}
