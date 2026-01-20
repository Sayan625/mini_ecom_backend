package com.example.ecom.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecom.Repositories.CategoryRepository;
import com.example.ecom.model.Category;
import com.example.ecom.services.CategoryService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CategoryController {

    private final CategoryService repo;

    public CategoryController(CategoryService repo) {
        this.repo = repo;
    }

    @GetMapping("/categories")
    public List<Category> all() {
        return repo.getAll();
    }

    @PostMapping("/admin/categories")
    public Category create(@RequestBody Category c) {
        return repo.save(c);
    }

    @DeleteMapping("/admin/categories/{id}")
    public String delete(@PathVariable long id) {
        return repo.delete(id);

        }
}
