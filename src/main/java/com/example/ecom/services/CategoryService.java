package com.example.ecom.services;


import java.util.List;
import org.springframework.stereotype.Service;

import com.example.ecom.Repositories.CategoryRepository;
import com.example.ecom.model.Category;

@Service
public class CategoryService {

    private final CategoryRepository repo;

    public CategoryService(CategoryRepository repo) {
        this.repo = repo;
    }

    public List<Category> getAll() {
        return repo.findAll();
    }

    public Category save(Category p) {
        return repo.save(p);
    }

    public String delete(Long id) {
        try {
            
            repo.deleteById(id);
            return "Category deleted";
        } catch (Exception e) {

            return e.getMessage();
        }
    }


}
