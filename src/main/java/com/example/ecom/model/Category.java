package com.example.ecom.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Entity
@Data
public class Category {

    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "cart_item_seq"
    )
    @SequenceGenerator(
        name = "cart_item_seq",
        sequenceName = "cart_item_sequence",
        allocationSize = 1
    )
    private Long id;
    private String name;

    @JsonIgnore
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @JsonIgnore
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
