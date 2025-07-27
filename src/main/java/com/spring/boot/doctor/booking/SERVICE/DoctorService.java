package com.spring.boot.doctor.booking.SERVICE;

import java.util.List;

import com.spring.boot.doctor.booking.DOCTOR.ADMIN.DTO.DoctorAdminDto;
import com.spring.boot.doctor.booking.ENTITY.Doctor;
import com.spring.boot.doctor.bookingDTOs.AppointMentResponseListDto;
import com.spring.boot.doctor.bookingDTOs.DoctorRequestDto;
import com.spring.boot.doctor.bookingDTOs.DoctorResponseDto;

public interface DoctorService {

    DoctorResponseDto registerDoctor(DoctorRequestDto dto);

    List<DoctorResponseDto> searchDoctors(String specialization, String location);

    void updateAvailabilityStatus(Long doctorId, String status);
    
    List<DoctorAdminDto> getAllDoctorsForAdmin();
    void updateDoctorStatus(Long doctorId, Doctor.Status status);

	List<AppointMentResponseListDto> checkAppointmentsRequests(Long doctorId);
}
