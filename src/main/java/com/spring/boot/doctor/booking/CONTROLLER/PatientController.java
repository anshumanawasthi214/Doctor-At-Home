package com.spring.boot.doctor.booking.CONTROLLER;

import com.spring.boot.doctor.booking.DTOs.PatientRequestDto;
import com.spring.boot.doctor.booking.DTOs.PatientResponseDto;
import com.spring.boot.doctor.booking.SERVICE.PatientService;
import com.spring.boot.doctor.booking.UTIL.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<PatientResponseDto> registerPatient(@RequestBody PatientRequestDto dto) {
    	System.out.println(dto);
        return ResponseEntity.ok(patientService.registerPatient(dto));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getPatient(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(patientService.getPatientByUserId(id));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updatePatient(@RequestBody PatientRequestDto dto, HttpServletRequest request) {
        Long userId = getUserIdFromRequest(request);
        return ResponseEntity.ok(patientService.updatePatientByUserId(userId, dto));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deletePatient(HttpServletRequest request) {
        Long userId = getUserIdFromRequest(request);
        patientService.deletePatientByUserId(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/history/appointments")
    public ResponseEntity<?> getBookingHistory(HttpServletRequest request) {
        Long userId = getUserIdFromRequest(request);
        return ResponseEntity.ok(patientService.getBookingHistoryByUserId(userId));
    }

    private Long getUserIdFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            return jwtUtil.extractUserId(token);
        }
        return null;
    }
}
