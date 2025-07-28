package com.spring.boot.doctor.booking.Mapper;

import org.springframework.stereotype.Component;

import com.spring.boot.doctor.booking.DTOs.DoctorResponseDto;
import com.spring.boot.doctor.booking.ENTITY.Doctor;

@Component
public class DoctorMapper {
    public DoctorResponseDto toDto(Doctor doctor) {
    	    DoctorResponseDto dto = new DoctorResponseDto();
    	    dto.setId(doctor.getId());
    	    dto.setName(doctor.getName());
    	    dto.setEmail(doctor.getEmail());
    	    dto.setPhone(doctor.getPhone());
    	    dto.setSpecialization(doctor.getSpecialization());
    	    dto.setLocation(doctor.getLocation());
    	    dto.setAvailability(doctor.getAvailability());
    	    dto.setStatus(doctor.getStatus() != null ? doctor.getStatus().name() : null);
    	    dto.setAvailabilityStatus(doctor.getAvailabilityStatus() != null ? doctor.getAvailabilityStatus().name() : null);
    	    dto.setQualification(doctor.getQualification());
    	    dto.setYearsOfExperience(doctor.getYearsOfExperience());
    	    dto.setConsultationFee(doctor.getConsultationFee());
    	    dto.setRatings(doctor.getRatings());
    	    dto.setProfilePicture(doctor.getProfilePicture());
    	    dto.setLanguages(doctor.getLanguages());
    	    dto.setAbout(doctor.getAbout());
    	    dto.setSpecialties(doctor.getSpecialties());
    	    dto.setCreatedAt(doctor.getCreatedAt());
    	    dto.setUpdatedAt(doctor.getUpdatedAt());
    	    return dto;
    	}
}
