package com.spring.boot.doctor.booking.SERVICE.IMPLEMENTATION;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.boot.doctor.booking.DTOs.RegisterUserRequest;
import com.spring.boot.doctor.booking.DTOs.ResponseUserDetails;
import com.spring.boot.doctor.booking.ENTITY.Users;
import com.spring.boot.doctor.booking.REPOSITORY.UsersRepository;
import com.spring.boot.doctor.booking.SERVICE.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsersRepository userRepo;

    @Override
    public ResponseUserDetails registerUser(RegisterUserRequest request) {
        Optional<Users> existingUserOpt = userRepo.findByUsername(request.getUsername());

        if (existingUserOpt.isPresent()) {
            Users existing = existingUserOpt.get();
            return new ResponseUserDetails(existing.getId(), existing.getUsername(), existing.getPassword(), existing.getRole().name());
        }

        Users user = new Users();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        Users saved = userRepo.save(user);
        return new ResponseUserDetails(saved.getId(), saved.getUsername(), saved.getPassword(), saved.getRole().name());
    }

    @Override
    public ResponseUserDetails getUserDetailsByUsername(String username) {
        Users user = userRepo.findByUsername(username).orElseThrow();
        return new ResponseUserDetails(user.getId(), user.getUsername(), user.getPassword(), user.getRole().name());
    }

	@Override
	public String deletePatientByUserId(Long userId) {
		userRepo.deleteById(userId);
		return "User is deleted Successfully";
	}

	@Override
	public String deleteDoctorByUserId(Long userId) {
		userRepo.deleteById(userId);
		return userId+" is deleted Successfully";
	}
}
