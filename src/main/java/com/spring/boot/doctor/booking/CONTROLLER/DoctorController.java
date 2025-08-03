package com.spring.boot.doctor.booking.CONTROLLER;


import com.spring.boot.doctor.booking.DTOs.AppointmentResponseDto;
import com.spring.boot.doctor.booking.DTOs.DoctorRequestDto;
import com.spring.boot.doctor.booking.DTOs.DoctorResponseDto;
import com.spring.boot.doctor.booking.REPOSITORY.UsersRepository;
import com.spring.boot.doctor.booking.SERVICE.DoctorService;
import com.spring.boot.doctor.booking.SERVICE.UserService;
import com.spring.boot.doctor.booking.SERVICE.IMPLEMENTATION.TokenBlacklistService;
import com.spring.boot.doctor.booking.UTIL.JWTUtil;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private JWTUtil jwtUtil;
    
    @Autowired
    private TokenBlacklistService blacklistService;
    
    @Autowired
    UsersRepository usersRepository;
    
    @Autowired
    UserService userService;

    // Unauthenticated search
    @GetMapping("/search")
    public ResponseEntity<List<DoctorResponseDto>> searchDoctors(
        @RequestParam(required = false) Long id,
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String specialization,
        @RequestParam(required = false) String location,
        @RequestParam(required = false) Double minFee,
        @RequestParam(required = false) Double maxFee,
        @RequestParam(required = false) Double minRatings,
        @RequestParam(required = false) Integer experience,
        @RequestParam(required = false) String languages,
        @RequestParam(required = false) String availability) {

        List<DoctorResponseDto> result = doctorService
                .searchDoctorsWithFilters(id, name, specialization, location, minFee, maxFee, minRatings, experience, languages, availability)
                .stream()
                .map(doctor -> doctorService.mapToResponseDto(doctor))
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    // Self update (authenticated doctor only)
    @PutMapping("/update")
    public ResponseEntity<DoctorResponseDto> updateDoctor(
        @RequestBody DoctorRequestDto dto,
        HttpServletRequest request) {
    	 Long userId = getUserIdFromRequest(request);
         return ResponseEntity.ok(doctorService.updateDoctor(userId, dto));
    }

    // Self delete (authenticated doctor only)
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteDoctor(HttpServletRequest request) {
    	  Long userId = getUserIdFromRequest(request);
          userService.deleteDoctorByUserId(userId);
          return ResponseEntity.ok(userService.deleteDoctorByUserId(userId));
    }

    // Register doctor (unauthenticated)
    @PostMapping("/register-doctor")
    public ResponseEntity<DoctorResponseDto> registerDoctor(@RequestBody DoctorRequestDto dto) {
        return ResponseEntity.ok(doctorService.registerDoctor(dto));
    }

    // Get by ID (open)
    @GetMapping("/get/{id}")
    public ResponseEntity<DoctorResponseDto> getDoctorById(@PathVariable Long id) {
        return ResponseEntity.ok(doctorService.getDoctorById(id));
    }
    
    
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            blacklistService.blacklistToken(token);
            return ResponseEntity.ok("Logged out successfully.");
        }

        return ResponseEntity.badRequest().body("No token found.");
    }
    
    private Long getUserIdFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            return jwtUtil.extractUserId(token);
        }
        return null;
    }
    
    
    // --- 1. Get pending appointments for authenticated doctor ---
    @GetMapping("/appointments/pending")
    public ResponseEntity<List<AppointmentResponseDto>> getPendingAppointments(HttpServletRequest request) {
        Long userId = getUserIdFromRequest(request);
        List<AppointmentResponseDto> pendingAppointments = doctorService.getPendingAppointments(userId);
        return ResponseEntity.ok(pendingAppointments);
    }

    // --- 2. Respond to appointment request (accept/reject) ---
    @PostMapping("/appointments/{appointmentId}/response")
    public ResponseEntity<String> respondToAppointment(
            @PathVariable Long appointmentId,
            @RequestParam String response) {

        if (!response.equalsIgnoreCase("ACCEPTED") && !response.equalsIgnoreCase("REJECTED")) {
            return ResponseEntity.badRequest().body("Response must be 'ACCEPTED' or 'REJECTED'");
        }

        doctorService.respondToAppointment(appointmentId, response.toUpperCase());
        return ResponseEntity.ok("Appointment " + response.toUpperCase());
    }

    // --- 3. Download document attached to an appointment ---
    @GetMapping("/appointments/{appointmentId}/documents/{documentId}/download")
    public ResponseEntity<Resource> downloadDocument(
            @PathVariable Long appointmentId,
            @PathVariable Long documentId) {

        Resource document = doctorService.downloadDocument(appointmentId, documentId);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + document.getFilename() + "\"")
                .body(document);
    }

    
    
    
}
