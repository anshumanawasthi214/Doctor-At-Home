package com.spring.boot.doctor.booking.DOCTOR.ADMIN.DTO;

import com.spring.boot.doctor.booking.ENTITY.Doctor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorAdminDto {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String specialization;
    private String location;
    private Doctor.Status status;
    private Doctor.AvailabilityStatus availabilityStatus;
}