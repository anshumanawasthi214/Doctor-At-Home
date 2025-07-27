package com.spring.boot.doctor.booking.CONTROLLER;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.spring.boot.doctor.booking.SERVICE.AppointmentService;
import com.spring.boot.doctor.bookingDTOs.AppointmentRequestDto;
import com.spring.boot.doctor.bookingDTOs.AppointmentResponseDto;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/book")
    public ResponseEntity<AppointmentResponseDto> bookAppointment(@RequestBody AppointmentRequestDto dto) {
        return ResponseEntity.ok(appointmentService.bookAppointment(dto));
    }

    @PutMapping("/{appointmentId}/status")
    public ResponseEntity<String> updateStatus(
            @PathVariable Long appointmentId,
            @RequestParam String status) {
        appointmentService.updateAppointmentStatus(appointmentId, status);
        return ResponseEntity.ok("Appointment status updated to: " + status);
    }

    @GetMapping("/doctor/{doctorId}/history")
    public ResponseEntity<List<AppointmentResponseDto>> getDoctorBookingHistory(@PathVariable Long doctorId) {
        return ResponseEntity.ok(appointmentService.getDoctorBookingHistory(doctorId));
    }

    @GetMapping("/patient/{patientId}/history")
    public ResponseEntity<List<AppointmentResponseDto>> getPatientBookingHistory(@PathVariable Long patientId) {
        return ResponseEntity.ok(appointmentService.getPatientBookingHistory(patientId));
    }
}

