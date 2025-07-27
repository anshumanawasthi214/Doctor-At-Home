package com.spring.boot.doctor.booking.CONTROLLER;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.spring.boot.doctor.booking.SERVICE.PatientService;
import com.spring.boot.doctor.bookingDTOs.PatientRequestDto;
import com.spring.boot.doctor.bookingDTOs.PatientResponseDto;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping("/register")
    public ResponseEntity<PatientResponseDto> registerPatient(@RequestBody PatientRequestDto dto) {
        return ResponseEntity.ok(patientService.registerPatient(dto));
    }

    @GetMapping("/{patientId}/history")
    public ResponseEntity<?> getBookingHistory(@PathVariable Long patientId) {
        return ResponseEntity.ok(patientService.getBookingHistory(patientId));
    }
}
