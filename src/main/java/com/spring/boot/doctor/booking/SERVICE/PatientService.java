package com.spring.boot.doctor.booking.SERVICE;

import java.util.List;

import com.spring.boot.doctor.booking.DTOs.AppointmentResponseDto;
import com.spring.boot.doctor.booking.DTOs.PatientAdminDto;
import com.spring.boot.doctor.booking.DTOs.PatientRequestDto;
import com.spring.boot.doctor.booking.DTOs.PatientResponseDto;

public interface PatientService {

    PatientResponseDto registerPatient(PatientRequestDto dto);

    PatientResponseDto getPatientById(Long patientId);

    PatientResponseDto updatePatient(Long patientId, PatientRequestDto dto);

    void deletePatient(Long patientId);

    List<AppointmentResponseDto> getBookingHistory(Long patientId);

    List<PatientAdminDto> getAllPatientsForAdmin();
}
