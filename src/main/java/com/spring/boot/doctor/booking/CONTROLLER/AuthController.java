package com.spring.boot.doctor.booking.CONTROLLER;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.boot.doctor.booking.DTOs.AuthRequest;
import com.spring.boot.doctor.booking.UTIL.JWTUtil;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;


@RestController
public class AuthController {

	
	@Autowired
	AuthenticationManager authenticationManager;
	
	
	@Autowired
	JWTUtil jwtUtil;
	
	
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> generateToken(@RequestBody AuthRequest authRequest) {
	    try {
	        authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
	        );

	        String token = jwtUtil.generateToken(authRequest.getUsername());
	        Long userId = jwtUtil.extractUserId(token);
	        String role = jwtUtil.extractRole(token);

	        Map<String, Object> response = new HashMap<>();
	        response.put("token", token);
	        response.put("userId", userId);
	        response.put("role", role);

	        return ResponseEntity.ok(response);

	    } catch (Exception e) {
	        return ResponseEntity.status(401).body("Invalid credentials");
	    }
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
