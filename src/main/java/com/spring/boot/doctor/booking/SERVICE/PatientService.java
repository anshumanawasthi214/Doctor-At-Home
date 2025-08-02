package com.spring.boot.doctor.booking.SERVICE;

import java.util.List;

import com.spring.boot.doctor.booking.DTOs.AppointmentResponseDto;
import com.spring.boot.doctor.booking.DTOs.PatientAdminDto;
import com.spring.boot.doctor.booking.DTOs.PatientRequestDto;
import com.spring.boot.doctor.booking.DTOs.PatientResponseDto;

public interface PatientService {
    PatientResponseDto registerPatient(PatientRequestDto dto);
    PatientResponseDto getCurrentPatient();
    PatientResponseDto updatePatientByUserId(Long userId, PatientRequestDto dto);
    void deletePatientByUserId(Long userId);
    List<AppointmentResponseDto> getBookingHistoryByUserId(Long userId);
    List<PatientAdminDto> getAllPatientsForAdmin();
}
