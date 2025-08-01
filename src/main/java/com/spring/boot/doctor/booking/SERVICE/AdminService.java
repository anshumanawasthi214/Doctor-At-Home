package com.spring.boot.doctor.booking.SERVICE;


import java.util.List;
import java.util.Optional;

import com.spring.boot.doctor.booking.ENTITY.Admin;

public interface AdminService {
	  Admin createAdmin(Admin admin);
	    Admin updateAdmin(Admin admin);
	    void deleteAdmin(Long id);
	    Optional<Admin> getAdminById(Long id);
	    Optional<Admin> getAdminByEmail(String email);
	    List<Admin> getAllAdmins();
}
