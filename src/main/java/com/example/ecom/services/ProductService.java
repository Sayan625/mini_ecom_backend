package com.example.ecom.services;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import com.example.ecom.Repositories.CategoryRepository;
import com.example.ecom.Repositories.ProductRepository;
import com.example.ecom.model.Product;
import com.example.ecom.model.Category;

@Service
public class ProductService {

    private final ProductRepository repo;
    private final CategoryRepository categoryRepo;

    public ProductService(ProductRepository repo, CategoryRepository categoryRepo) {
        this.repo = repo;
        this.categoryRepo = categoryRepo;
    }

    public List<Product> getAll() {
        return repo.findAll();
    }

    public Product save(Product p) {
        Category category = categoryRepo.findById(p.getCategory().getId())
                .orElseThrow();
        p.setCategory(category);
        return repo.save(p);
    }

    public String delete(Long id) {

        try {

            repo.deleteById(id);
            return "product deleted";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public Page<Product> search(int page,
            int size, String name, Long categoryId) {

        Pageable pageable = PageRequest.of(page, size);

        if (name != null && categoryId != null) {
            return repo.findByNameContainingIgnoreCaseAndCategoryId(name, categoryId,pageable);
        }

        if (name != null) {
            return repo.findByNameContainingIgnoreCase(name,pageable);
        }

        if (categoryId != null) {
            return repo.findByCategoryId(categoryId,pageable);
        }

        return repo.findAll(pageable);
    }

    public Product update(Long id, Product updatedProduct) {

        Product existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (updatedProduct.getCategory() != null &&
                updatedProduct.getCategory().getId() != null) {

            Category category = categoryRepo
                    .findById(updatedProduct.getCategory().getId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));

            existing.setCategory(category);
        }

        if (updatedProduct.getName() != null)
            existing.setName(updatedProduct.getName());

        if (updatedProduct.getDescription() != null)
            existing.setDescription(updatedProduct.getDescription());

        if (updatedProduct.getPrice() != null)
            existing.setPrice(updatedProduct.getPrice());

        if (updatedProduct.getStock() != null)
            existing.setStock(updatedProduct.getStock());

        if (updatedProduct.getBgColor() != null)
            existing.setBgColor(updatedProduct.getBgColor());

        if (updatedProduct.getFeatured() != null)
            existing.setFeatured(updatedProduct.getFeatured());

        return repo.save(existing);
    }

}
