package com.spring.boot.doctor.booking.SERVICE;


import java.util.List;

import com.spring.boot.doctor.bookingDTOs.AppointmentRequestDto;
import com.spring.boot.doctor.bookingDTOs.AppointmentResponseDto;

public interface AppointmentService {

    AppointmentResponseDto bookAppointment(AppointmentRequestDto dto);

    void updateAppointmentStatus(Long appointmentId, String status);

    List<AppointmentResponseDto> getDoctorBookingHistory(Long doctorId);

    List<AppointmentResponseDto> getPatientBookingHistory(Long patientId);
}
