package com.example.ecom.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecom.auth.JwtUtil;
import com.example.ecom.model.User;
import com.example.ecom.services.UserService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class UserController {

    private final UserService service;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService repo, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.service = repo;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User req) {
        User user = service.getByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(
                req.getPassword(),
                user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        String token = jwtUtil.generateToken(user.getEmail(),"USER");
        Map<String, String> response = new HashMap<>();

        response.put("id", user.getId().toString());
        response.put("name", user.getName());
        response.put("email", user.getEmail());
        response.put("phone", user.getPhone());
        response.put("address", user.getAddress());
        response.put("role", user.getRole());
        response.put("token", token);
        return response;
    }


    @GetMapping("/user/{id}")
    public Map<String, String> get(@PathVariable Long id) {

        User authUser = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        if (!authUser.getId().equals(id)) {
            throw new RuntimeException("Unauthorized access");
        }
        User saved = service.get(id).orElseThrow(() -> new RuntimeException("User not found"));
        Map<String, String> response = new HashMap<>();
        response.put("id", saved.getId().toString());
        response.put("name", saved.getName());
        response.put("email", saved.getEmail());
        response.put("phone", saved.getPhone());
        response.put("address", saved.getAddress());
        return response;
    }

    @PostMapping("/register")
    public String create(@RequestBody User user) {
        service.create(user);
       
        return "User created successfully";
    }

    @PutMapping("/user/{id}")
    public Map<String, String> update(@PathVariable Long id, @RequestBody User user) {

        User authUser = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        if (!authUser.getId().equals(id)) {
            throw new RuntimeException("Unauthorized access");
        }

        User saved = service.update(id, user);

        Map<String, String> response = new HashMap<>();
        response.put("id", saved.getId().toString());
        response.put("name", saved.getName());
        response.put("email", saved.getEmail());
        response.put("phone", saved.getPhone());
        response.put("address", saved.getAddress());
       
        return response;
    }

    @DeleteMapping("/user/{id}")
    public String delete(@PathVariable Long id) {
                User authUser = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        if (!authUser.getId().equals(id)) {
            throw new RuntimeException("Unauthorized access");
        }
        return service.delete(id);
    }
}
