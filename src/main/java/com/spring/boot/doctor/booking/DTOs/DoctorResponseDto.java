package com.spring.boot.doctor.booking.DTOs;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DoctorResponseDto {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String specialization;
    private String location;
    private String availability;           // e.g. "Mon-Fri, 9AM-5PM"
    private String status;                 // PENDING, APPROVED, REJECTED
    private String availabilityStatus;    // ACTIVE, LEAVE, UNAVAILABLE, etc.

    private String qualification;
    private Integer yearsOfExperience;
    private Double consultationFee;
    private Double ratings;
    private String profilePicture;
    private String languages;
    private String about;
    private String specialties;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
