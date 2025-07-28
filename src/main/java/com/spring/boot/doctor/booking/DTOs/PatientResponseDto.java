package com.spring.boot.doctor.booking.DTOs;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class PatientResponseDto {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private LocalDate dateOfBirth;
    private String gender;
    private String emergencyContact;
    private String medicalHistory;
    private String bloodGroup;
    private String allergies;
    private String profilePicture;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
