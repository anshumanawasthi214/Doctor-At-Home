package com.spring.boot.doctor.booking.CONTROLLER;
import com.spring.boot.doctor.booking.DTOs.PatientRequestDto;

import com.spring.boot.doctor.booking.DTOs.PatientResponseDto;
import com.spring.boot.doctor.booking.SERVICE.PatientService;
import com.spring.boot.doctor.booking.SERVICE.UserService;
import com.spring.boot.doctor.booking.SERVICE.IMPLEMENTATION.TokenBlacklistService;
import com.spring.boot.doctor.booking.UTIL.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

	@Autowired
	private UserService userService;
	
    @Autowired
    private PatientService patientService;

    @Autowired
    private JWTUtil jwtUtil;
    
    
    @Autowired
    private TokenBlacklistService blacklistService;

    @PostMapping("/register")
    public ResponseEntity<PatientResponseDto> registerPatient(@RequestBody PatientRequestDto dto) {
    	System.out.println(dto);
        return ResponseEntity.ok(patientService.registerPatient(dto));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getPatient() {
        return ResponseEntity.ok(patientService.getCurrentPatient());
        }

    @PutMapping("/update")
    public ResponseEntity<?> updatePatient(@RequestBody PatientRequestDto dto, HttpServletRequest request) {
        Long userId = getUserIdFromRequest(request);
        return ResponseEntity.ok(patientService.updatePatientByUserId(userId, dto));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deletePatient(HttpServletRequest request) {
        Long userId = getUserIdFromRequest(request);
        userService.deletePatientByUserId(userId);
        return ResponseEntity.ok(userService.deletePatientByUserId(userId));
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
