package com.spring.boot.doctor.booking.SERVICE.IMPLEMENTATION;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.boot.doctor.booking.ENTITY.Admin;
import com.spring.boot.doctor.booking.REPOSITORY.AdminRepository;
import com.spring.boot.doctor.booking.SERVICE.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

	  @Autowired
	    private AdminRepository adminRepository;

	    @Override
	    public Admin createAdmin(Admin admin) {
	        return adminRepository.save(admin);
	    }

	    @Override
	    public Admin updateAdmin(Admin admin) {
	        // You might want to check if admin exists before updating
	        return adminRepository.save(admin);
	    }

	    @Override
	    public void deleteAdmin(Long id) {
	        adminRepository.deleteById(id);
	    }

	    @Override
	    public Optional<Admin> getAdminById(Long id) {
	        return adminRepository.findById(id);
	    }

	    @Override
	    public Optional<Admin> getAdminByEmail(String email) {
	        return adminRepository.findByEmail(email);
	    }

	    @Override
	    public List<Admin> getAllAdmins() {
	        return adminRepository.findAll();
	    }

}
