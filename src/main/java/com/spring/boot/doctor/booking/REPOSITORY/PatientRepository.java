package com.spring.boot.doctor.booking.REPOSITORY;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.boot.doctor.booking.ENTITY.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
}
