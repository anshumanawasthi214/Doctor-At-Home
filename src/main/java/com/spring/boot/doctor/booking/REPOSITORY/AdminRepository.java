package com.spring.boot.doctor.booking.REPOSITORY;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.spring.boot.doctor.booking.ENTITY.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
	 Optional<Admin> findByEmail(String email);
}
