package com.spring.boot.doctor.booking.CONTROLLER;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.spring.boot.doctor.booking.SERVICE.DoctorService;
import com.spring.boot.doctor.bookingDTOs.AppointMentResponseListDto;
import com.spring.boot.doctor.bookingDTOs.DoctorAvailabilityUpdateDto;
import com.spring.boot.doctor.bookingDTOs.DoctorRequestDto;
import com.spring.boot.doctor.bookingDTOs.DoctorResponseDto;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping("/register")
    public ResponseEntity<DoctorResponseDto> registerDoctor(@RequestBody DoctorRequestDto dto) {
        return ResponseEntity.ok(doctorService.registerDoctor(dto));
    }

    @GetMapping("/search")
    public ResponseEntity<List<DoctorResponseDto>> searchDoctors(
            @RequestParam String specialization,
            @RequestParam String location) {
        return ResponseEntity.ok(doctorService.searchDoctors(specialization, location));
    }

    @PutMapping("/{doctorId}/availability")
    public ResponseEntity<String> updateAvailability(
            @PathVariable Long doctorId,
            @RequestBody DoctorAvailabilityUpdateDto dto) {
        doctorService.updateAvailabilityStatus(doctorId, dto.getAvailabilityStatus());
        return ResponseEntity.ok("Doctor availability updated to: " + dto.getAvailabilityStatus());
    }
    
    @GetMapping("/check/appointments/{doctorId}")
    public ResponseEntity<List<AppointMentResponseListDto>> checkAppointmentsRequest(@PathVariable Long doctorId) {
    	return ResponseEntity.ok(doctorService.checkAppointmentsRequests(doctorId));
    }
}
