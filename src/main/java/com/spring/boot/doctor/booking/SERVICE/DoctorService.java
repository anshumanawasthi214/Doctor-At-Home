package com.spring.boot.doctor.booking.SERVICE;




import java.util.List;

import com.spring.boot.doctor.booking.DTOs.DoctorAdminDto;
import com.spring.boot.doctor.booking.DTOs.DoctorRequestDto;
import com.spring.boot.doctor.booking.DTOs.DoctorResponseDto;
import com.spring.boot.doctor.booking.ENTITY.Doctor;
import com.spring.boot.doctor.booking.ENTITY.Doctor.Status;

public interface DoctorService {

    DoctorResponseDto registerDoctor(DoctorRequestDto dto);

    DoctorResponseDto getDoctorById(Long doctorId);

    DoctorResponseDto updateDoctor(Long userId, DoctorRequestDto dto);

    void deleteDoctor(Long doctorId);
    
	List<DoctorAdminDto> getAllDoctorsForAdmin();

	void updateDoctorStatus(Long id, Status status);

	List<Doctor> searchDoctorsWithFilters(Long id, String name, String specialization, String location,
            Double minFee, Double maxFee, Double minRatings,
            Integer experience, String languages, String availability);
	
	DoctorResponseDto getCurrentDoctor();
	DoctorResponseDto updateCurrentDoctor(DoctorRequestDto dto);
	void deleteCurrentDoctor();
	
	DoctorResponseDto mapToResponseDto(Doctor doctor);

}
