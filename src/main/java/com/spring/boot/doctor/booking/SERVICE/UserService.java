package com.spring.boot.doctor.booking.SERVICE;

import com.spring.boot.doctor.booking.DTOs.RegisterUserRequest;
import com.spring.boot.doctor.booking.DTOs.ResponseUserDetails;

public interface UserService {
    ResponseUserDetails registerUser(RegisterUserRequest userRequest);
    ResponseUserDetails getUserDetailsByUsername(String username);
	String deletePatientByUserId(Long userId);
	String deleteDoctorByUserId(Long userId);
}
