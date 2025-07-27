package com.spring.boot.doctor.booking.SERVICE;


import java.util.List;

import com.spring.boot.doctor.bookingDTOs.AppointmentResponseDto;
import com.spring.boot.doctor.bookingDTOs.PatientAdminDto;
import com.spring.boot.doctor.bookingDTOs.PatientRequestDto;
import com.spring.boot.doctor.bookingDTOs.PatientResponseDto;


public interface PatientService {

    PatientResponseDto registerPatient(PatientRequestDto dto);

    List<AppointmentResponseDto> getBookingHistory(Long patientId);

	List<PatientAdminDto> getAllPatientsForAdmin();
}
