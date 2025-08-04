package com.spring.boot.doctor.booking.SERVICE.IMPLEMENTATION;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.spring.boot.doctor.booking.DTOs.AdminCreationDto;
import com.spring.boot.doctor.booking.DTOs.AdminResponseDto;
import com.spring.boot.doctor.booking.ENTITY.Admin;
import com.spring.boot.doctor.booking.ENTITY.Users;
import com.spring.boot.doctor.booking.REPOSITORY.AdminRepository;
import com.spring.boot.doctor.booking.REPOSITORY.UsersRepository;
import com.spring.boot.doctor.booking.SERVICE.AdminService;

import jakarta.persistence.EntityNotFoundException;


@Service
public class AdminServiceImpl implements AdminService {

	  @Autowired
	    private AdminRepository adminRepository;

	  @Autowired
	  private UsersRepository usersRepository;

	    @Override
	    public AdminResponseDto createAdmin(AdminCreationDto dto) {
	    	 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	         String currentUsername = authentication.getName();

	         Users user = usersRepository.findByUsername(currentUsername)
	             .orElseThrow(() -> new RuntimeException("User not found: " + currentUsername));

	         // Prevent duplicate patient registration
	         if (adminRepository.findByUser(user).isPresent()) {
	             throw new RuntimeException("Admin already registered for this user.");
	         }

	         Admin admin = new Admin();
	         admin.setName(dto.getName());
	         admin.setEmail(dto.getEmailId());
	         admin.setPhone(dto.getPhoneNumber());
	        

	         admin.setUser(user);
	         user.setAdmin(admin);

	         return mapToResponseDto(adminRepository.save(admin));   
	    }

	    private AdminResponseDto mapToResponseDto(Admin p) {
	    	AdminResponseDto dto = new AdminResponseDto();
	        dto.setId(p.getId());
	        dto.setName(p.getName());
	        dto.setEmailId(p.getEmail());
	        dto.setMobileNumber(p.getPhone());
	        return dto;
		}

	   
	    @Override
	    public Optional<Admin> getAdminByEmail(String email) {
	        return adminRepository.findByEmail(email);
	    }

	    @Override
	    public List<Admin> getAllAdmins() {
	        return adminRepository.findAll();
	    }

		@Override
		public AdminResponseDto getCurrentAdmin() {
			 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		        String currentUsername = authentication.getName();

		        Users user = usersRepository.findByUsername(currentUsername)
		            .orElseThrow(() -> new EntityNotFoundException("User not found: " + currentUsername));

		        Admin admin = adminRepository.findByUser(user)
		            .orElseThrow(() -> new EntityNotFoundException("Patient not found for user: " + currentUsername));

		        return mapToResponseDto(admin);
		}

		@Override
		public AdminResponseDto updateAdminByUserId(Long userId, AdminCreationDto dto) {
			 Users user = usersRepository.findById(userId).orElseThrow();
		        Admin admin = adminRepository.findByUser(user).orElseThrow();

		        if (dto.getName() != null) admin.setEmail(dto.getEmailId());
		        if (dto.getPhoneNumber() != null) admin.setPhone(dto.getPhoneNumber());
		       
		        return mapToResponseDto(adminRepository.save(admin));
		}
	}
