package com.spring.boot.doctor.booking.DTOs;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorAdminDto {
	private Long id;
    private String name;
    private String email;
    private String phone;
    private String specialization;
    private String location;
    private String availability;
    private String status;
    private String availabilityStatus;
    private String qualification;
    private Integer yearsOfExperience;
    private Double consultationFee;
    private String specialties;
}
