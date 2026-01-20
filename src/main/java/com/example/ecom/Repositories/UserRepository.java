package com.example.ecom.Repositories;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ecom.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
