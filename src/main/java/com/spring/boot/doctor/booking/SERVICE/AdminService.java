package com.spring.boot.doctor.booking.SERVICE;


import java.util.List;
import java.util.Optional;

import com.spring.boot.doctor.booking.DTOs.AdminCreationDto;
import com.spring.boot.doctor.booking.DTOs.AdminResponseDto;
import com.spring.boot.doctor.booking.ENTITY.Admin;

public interface AdminService {
	AdminResponseDto createAdmin(AdminCreationDto admin);
    Optional<Admin> getAdminByEmail(String email); // internal use
    List<Admin> getAllAdmins(); // if you still need this
	AdminResponseDto getCurrentAdmin();
	AdminResponseDto updateAdminByUserId(Long userId, AdminCreationDto dto);
}
