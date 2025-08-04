package com.spring.boot.doctor.booking.ENTITY;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "admins")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String phone;
    
    @OneToOne
    @JoinColumn(name = "user_id",nullable=false)
    private Users user;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
    
    
 // ...
   

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
