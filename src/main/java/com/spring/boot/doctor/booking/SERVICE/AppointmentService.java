package com.spring.boot.doctor.booking.SERVICE;

import com.spring.boot.doctor.booking.DTOs.AppointmentRequestDto;
import com.spring.boot.doctor.booking.DTOs.AppointmentResponseDto;
import com.spring.boot.doctor.booking.ENTITY.Appointment.Status;

import java.util.List;

public interface AppointmentService {

    AppointmentResponseDto bookAppointment(AppointmentRequestDto dto);

    AppointmentResponseDto getAppointmentById(Long appointmentId);

    List<AppointmentResponseDto> getAppointmentsByDoctor(Long doctorId);

    List<AppointmentResponseDto> getAppointmentsByPatient(Long patientId);

    AppointmentResponseDto updateAppointment(Long appointmentId, AppointmentRequestDto dto);

    void cancelAppointment(Long appointmentId);

	String changeAppointmentStatus(Long appointmentId, Status status);
}
