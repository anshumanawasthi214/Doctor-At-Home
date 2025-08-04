package com.spring.boot.doctor.booking.ADMIN_CONTROLLER;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.spring.boot.doctor.booking.DTOs.AdminCreationDto;
import com.spring.boot.doctor.booking.DTOs.AdminResponseDto;
import com.spring.boot.doctor.booking.DTOs.DoctorAdminDto;
import com.spring.boot.doctor.booking.DTOs.PatientAdminDto;
import com.spring.boot.doctor.booking.DTOs.PatientRequestDto;
import com.spring.boot.doctor.booking.ENTITY.Admin;
import com.spring.boot.doctor.booking.ENTITY.Doctor;
import com.spring.boot.doctor.booking.SERVICE.AdminService;
import com.spring.boot.doctor.booking.SERVICE.DoctorService;
import com.spring.boot.doctor.booking.SERVICE.PatientService;
import com.spring.boot.doctor.booking.SERVICE.UserService;
import com.spring.boot.doctor.booking.SERVICE.IMPLEMENTATION.TokenBlacklistService;
import com.spring.boot.doctor.booking.UTIL.JWTUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired private AdminService adminService;
    @Autowired private DoctorService doctorService;
    @Autowired private PatientService patientService;
    @Autowired private TokenBlacklistService blacklistService;
    @Autowired private JWTUtil jwtUtil;
    @Autowired private UserService userService;

    // ✅ Create Admin (use only in dev or by another super admin)
    @PostMapping("/create")
    public ResponseEntity<AdminResponseDto> createAdmin(@RequestBody AdminCreationDto admin) {
    	System.out.println(admin);
        return ResponseEntity.ok(adminService.createAdmin(admin));
    }

    // ✅ Get logged-in admin info
    @GetMapping("/me")
    public ResponseEntity<?> getMyDetails() {
    	return ResponseEntity.ok(adminService.getCurrentAdmin());
    }

    // ✅ Update logged-in admin only
    @PutMapping("/update")
    public ResponseEntity<AdminResponseDto> updateAdmin(@RequestBody AdminCreationDto dto, HttpServletRequest request) {
        Long userId = getUserIdFromRequest(request);
        return ResponseEntity.ok(adminService.updateAdminByUserId(userId, dto));
    }

    // ✅ Delete own admin account
    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteAdmin(HttpServletRequest request) {
        Long userId = getUserIdFromRequest(request);
        userService.deletePatientByUserId(userId);
        return ResponseEntity.ok(userService.deleteAdminByUserId(userId));
    }
    // ✅ View all registered doctors (with limited data)
    @GetMapping("/doctors")
    public ResponseEntity<List<DoctorAdminDto>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctorsForAdmin());
    }

    // ✅ Approve or reject doctor
    @PutMapping("/doctors/{id}/status")
    public ResponseEntity<String> updateDoctorStatus(
            @PathVariable Long id,
            @RequestParam("status") String statusString) {

        try {
            Doctor.Status status = Doctor.Status.valueOf(statusString.toUpperCase());
            doctorService.updateDoctorStatus(id, status);
            return ResponseEntity.ok("Doctor status updated to " + status);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid status: " + statusString);
        }
    }

    // ✅ View patients with safe DTO
    @GetMapping("/patients")
    public ResponseEntity<List<PatientAdminDto>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatientsForAdmin());
    }
    
    
    private Long getUserIdFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            return jwtUtil.extractUserId(token);
        }
        return null;
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
}
