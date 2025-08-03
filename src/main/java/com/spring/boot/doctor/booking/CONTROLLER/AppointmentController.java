package com.spring.boot.doctor.booking.CONTROLLER;

import com.spring.boot.doctor.booking.DTOs.AppointmentRequestDto;
import com.spring.boot.doctor.booking.DTOs.AppointmentResponseDto;
import com.spring.boot.doctor.booking.SERVICE.AppointmentService;
import com.spring.boot.doctor.booking.UTIL.JWTUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patient/appointments")
public class AppointmentController {

    @Autowired private AppointmentService appointmentService;
    @Autowired private JWTUtil jwtUtil;

    @PostMapping("/book")
    public ResponseEntity<AppointmentResponseDto> bookAppointment(
            @Valid @RequestBody AppointmentRequestDto dto,
            HttpServletRequest request) {

        Long patientUserId = getUserId(request);
        AppointmentResponseDto response = appointmentService.bookAppointment(dto, patientUserId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/{appointmentId}")
    public ResponseEntity<AppointmentResponseDto> getAppointment(
            @PathVariable Long appointmentId,
            HttpServletRequest request) {

        Long patientUserId = getUserId(request);
        AppointmentResponseDto appt = appointmentService.getAppointmentById(appointmentId, patientUserId);
        return ResponseEntity.ok(appt);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AppointmentResponseDto>> getAllAppointments(HttpServletRequest request) {
        Long patientUserId = getUserId(request);
        return ResponseEntity.ok(appointmentService.getAppointmentsByPatient(patientUserId));
    }

    @PutMapping("/update/{appointmentId}")
    public ResponseEntity<AppointmentResponseDto> updateAppointment(
            @PathVariable Long appointmentId,
            @Valid @RequestBody AppointmentRequestDto dto,
            HttpServletRequest request) {

        Long patientUserId = getUserId(request);
        AppointmentResponseDto appt = appointmentService.updateAppointment(appointmentId, dto, patientUserId);
        return ResponseEntity.ok(appt);
    }

    @DeleteMapping("/delete/{appointmentId}")
    public ResponseEntity<Void> cancelAppointment(
            @PathVariable Long appointmentId,
            HttpServletRequest request) {

        Long patientUserId = getUserId(request);
        appointmentService.cancelAppointment(appointmentId, patientUserId);
        return ResponseEntity.noContent().build();
    }

    private Long getUserId(HttpServletRequest req) {
        String auth = req.getHeader("Authorization");
        if (auth != null && auth.startsWith("Bearer ")) {
            return jwtUtil.extractUserId(auth.substring(7));
        }
        return null;
    }
}
