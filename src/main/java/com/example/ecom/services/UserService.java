package com.example.ecom.services;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.ecom.Repositories.UserRepository;
import com.example.ecom.model.User;

@Service
public class UserService {

    private final UserRepository repo;
    
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }


    public List<User> getAll() {
        return repo.findAll();
    }

    public Optional<User> get(Long id) {
        return repo.findById(id);
    }

    public Optional<User> getByEmail(String email){
        return repo.findByEmail(email);
    }

    public User create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repo.save(user);
    }

    public User update(Long id,User user) {
        User u = repo.findById(id).orElseThrow();
        if(user.getName()!=null)
        u.setName(user.getName());

        if(user.getEmail()!=null)
        u.setEmail(user.getEmail());

        if(user.getPhone()!=null)
        u.setPhone(user.getPhone());

        if(user.getAddress()!=null)
        u.setAddress(user.getAddress());
        return repo.save(u);
    }

    public String delete(Long id) {
        try {
            repo.deleteById(id);
            return "user deleted";
            
        } catch (Exception e) {

            return e.getMessage();
        }
    }
}
