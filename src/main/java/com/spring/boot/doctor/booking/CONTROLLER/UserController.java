package com.spring.boot.doctor.booking.CONTROLLER;

import com.spring.boot.doctor.booking.DTOs.AuthRequest;

import com.spring.boot.doctor.booking.DTOs.AuthResponse;
import com.spring.boot.doctor.booking.DTOs.RegisterUserRequest;
import com.spring.boot.doctor.booking.DTOs.ResponseUserDetails;
import com.spring.boot.doctor.booking.ENTITY.Role;
import com.spring.boot.doctor.booking.SERVICE.UserService;
import com.spring.boot.doctor.booking.UTIL.JWTUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register/patient")
    @Operation(summary = "Create Patient's Account", security = @SecurityRequirement(name = ""))
    public ResponseEntity<ResponseUserDetails> registerPatient(@RequestBody RegisterUserRequest request) {
        request.setRole(Role.PATIENT);
        return ResponseEntity.ok(userService.registerUser(request));
    }

    @PostMapping("/register/doctor")
    @Operation(summary = "Create Doctor's Account", security = @SecurityRequirement(name = ""))

    public ResponseEntity<ResponseUserDetails> registerDoctor(@RequestBody RegisterUserRequest request) {
        request.setRole(Role.DOCTOR);
        return ResponseEntity.ok(userService.registerUser(request));
    }

    @PostMapping("/register/admin")
    public ResponseEntity<ResponseUserDetails> registerAdmin(@RequestBody RegisterUserRequest request) {
        request.setRole(Role.ADMIN);
        return ResponseEntity.ok(userService.registerUser(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        String token = jwtUtil.generateToken(authRequest.getUsername());
        ResponseUserDetails user = userService.getUserDetailsByUsername(authRequest.getUsername());
        return ResponseEntity.ok(new AuthResponse(token, user));
    }
    
  
}
