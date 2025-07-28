package com.spring.boot.doctor.booking.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientAdminDto {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String gender;
    private String bloodGroup;
}
