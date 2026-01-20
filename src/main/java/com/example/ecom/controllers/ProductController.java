package com.example.ecom.controllers;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecom.model.Product;
import com.example.ecom.model.User;
import com.example.ecom.services.ProductService;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/")
@CrossOrigin
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("products")
    public Page<Product> all(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long categoryId) {
        return service.search(page, size, name, categoryId);
    }

    @PostMapping("admin/products")
    public Product create(@RequestBody Product p) {
        User authUser = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        // if (!(authUser.getRole().equals("ADMIN"))) {
        // throw new RuntimeException("Unauthorized access");
        // }
        return service.save(p);
    }

    @PutMapping("admin/products/{id}")
    public Product putMethodName(@PathVariable long id, @RequestBody Product p) {
        User authUser = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        if (!(authUser.getRole().equals("ADMIN"))) {
            throw new RuntimeException("Unauthorized access");
        }
        return service.update(id, p);
    }

    @DeleteMapping("admin/products/{id}")
    public String delete(@PathVariable Long id) {
        User authUser = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        if (!(authUser.getRole().equals("ADMIN"))) {
            throw new RuntimeException("Unauthorized access");
        }
        return service.delete(id);
    }
}
