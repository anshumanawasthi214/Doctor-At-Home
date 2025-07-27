package com.spring.boot.doctor.bookingDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientRequestDto {
    private String name;
    private String email;
    private String phone;
}