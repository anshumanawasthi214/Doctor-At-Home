package com.spring.boot.doctor.booking.REPOSITORY;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.spring.boot.doctor.booking.ENTITY.Appointment;
import com.spring.boot.doctor.booking.ENTITY.Doctor;
import com.spring.boot.doctor.booking.ENTITY.Patient;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByDoctor(Doctor doctor);

    List<Appointment> findByPatient(Patient patient);
    
    List<Appointment> findAll();
    @Query("Select a from Appointment a where a.doctor.id=:doctorId")
	List<Appointment> findByDoctorId(@Param("doctorId") Long doctorId);
    
    List<Appointment> findByDoctorAndPatient(Doctor doctor, Patient patient);
}
 