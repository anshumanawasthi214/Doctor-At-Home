package com.spring.boot.doctor.booking.CONTROLLER;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.spring.boot.doctor.booking.SERVICE.IMPLEMENTATION.TokenBlacklistService;

@RestController
@RequestMapping("/user")
public class LogoutController {

    @Autowired
    private TokenBlacklistService blacklistService;

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