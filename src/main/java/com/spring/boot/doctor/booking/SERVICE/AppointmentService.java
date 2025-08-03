package com.spring.boot.doctor.booking.SERVICE;

import com.spring.boot.doctor.booking.DTOs.AppointmentRequestDto;
import com.spring.boot.doctor.booking.DTOs.AppointmentResponseDto;
import com.spring.boot.doctor.booking.ENTITY.Appointment;

import java.util.List;

public interface AppointmentService {
    AppointmentResponseDto bookAppointment(AppointmentRequestDto dto, Long patientUserId);
    AppointmentResponseDto getAppointmentById(Long appointmentId, Long patientUserId);
    List<AppointmentResponseDto> getAppointmentsByPatient(Long patientUserId);
    AppointmentResponseDto updateAppointment(Long appointmentId, AppointmentRequestDto dto, Long patientUserId);
    void cancelAppointment(Long appointmentId, Long patientUserId);
    String changeAppointmentStatus(Long appointmentId, Appointment.Status status);
}
