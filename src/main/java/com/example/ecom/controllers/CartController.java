package com.example.ecom.controllers;

import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecom.model.Cart;
import com.example.ecom.model.User;
import com.example.ecom.services.CartService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/user/cart")
@CrossOrigin
public class CartController {

    private final CartService service;

    public CartController(CartService service) {
        this.service = service;
    }

    @GetMapping("/{userId}")
    public Cart getCart(@PathVariable Long userId) {
        User authUser = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        if (!authUser.getId().equals(userId)) {
            throw new RuntimeException("Unauthorized access");
        }
        return service.getCart(userId);
    }

    @PostMapping("/add/{userId}")
    public Cart addItem(
            @PathVariable Long userId, @RequestBody Map<String, String> body) {
        User authUser = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        if (!authUser.getId().equals(userId)) {
            throw new RuntimeException("Unauthorized access");
        }
        Long productId = Long.valueOf(body.get("productId"));
        int qty = Integer.parseInt(body.get("qty"));
        return service.addItem(userId, productId, qty);
    }

    @PutMapping("/remove/{userId}")
    public Cart removeItem(
            @PathVariable Long userId,
            @RequestParam Long cartItemId) {
        User authUser = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        if (!authUser.getId().equals(userId)) {
            throw new RuntimeException("Unauthorized access");
        }
        return service.removeItem(userId, cartItemId);
    }

}
