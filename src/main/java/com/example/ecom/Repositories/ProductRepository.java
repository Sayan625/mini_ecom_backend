package com.example.ecom.Repositories;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ecom.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByNameContainingIgnoreCase(String name,Pageable pageable);

    Page<Product> findByCategoryId(Long categoryId,Pageable pageable);

    Page<Product> findByNameContainingIgnoreCaseAndCategoryId(
            String name,
            Long categoryId,
            Pageable pageable
        );
}
