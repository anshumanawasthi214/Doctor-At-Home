package com.spring.boot.doctor.bookingDTOs;

import lombok.Data;

@Data
public class DoctorResponseDto {
    private Long id;
    private String name;
    private String email;
    private String specialization;
    private String location;
    private String availability;
    private String status;             // e.g. PENDING, APPROVED
    private String availabilityStatus; // ACTIVE, LEAVE, UNAVAILABLE

    // Getters and Setters
}
