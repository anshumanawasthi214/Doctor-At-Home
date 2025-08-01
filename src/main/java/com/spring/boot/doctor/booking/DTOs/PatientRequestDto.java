package com.spring.boot.doctor.booking.DTOs;

import java.time.LocalDate;
import lombok.Data;

@Data
public class PatientRequestDto {

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

    private Long userId; // Fo
}
