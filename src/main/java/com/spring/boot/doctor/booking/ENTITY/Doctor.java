package com.spring.boot.doctor.booking.ENTITY;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

   
    private String email;

    private String phone;

    private String specialization;

    private String location;

    private String availability; // e.g. "Mon-Fri, 9AM-5PM"

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    @Enumerated(EnumType.STRING)
    private AvailabilityStatus availabilityStatus = AvailabilityStatus.ACTIVE;

    private String qualification;

    private Integer yearsOfExperience;

    private Double consultationFee;

    private Double ratings;

    private String profilePicture;

    private String languages;

    @Column(length = 1000)
    private String about;

    private String specialties; // consider a List<String> if you want multiple specialties

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public enum Status {
        PENDING, APPROVED, REJECTED,
    }

    public enum AvailabilityStatus {
        ACTIVE, LEAVE, UNAVAILABLE, NO, YES, AVAILABLE,
    }
    

}
