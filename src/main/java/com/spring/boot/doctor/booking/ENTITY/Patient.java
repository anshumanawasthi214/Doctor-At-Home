package com.spring.boot.doctor.booking.ENTITY;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    
   
    private String email;

    private String phone;

    private String address;

    private LocalDate dateOfBirth;

    private String gender;

    private String emergencyContact;

    @Column(length = 1000)
    private String medicalHistory;

    private String bloodGroup;

    private String allergies;

    private String profilePicture;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
    
    @OneToOne
    @JoinColumn(name = "user_id",nullable=false)
    private Users user;
    
    
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MedicalDocument> medicalDocuments = new ArrayList<>();
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
