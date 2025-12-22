package com.nt.hms.service;

import org.springframework.data.domain.Page;

import com.nt.hms.Entity.Doctor;

public interface IDoctorService {
	public Page<Doctor> getDoctorDirectory(int page, int size);
	public Doctor getDoctorProfileById(Long id);
	public Doctor createDoctorProfile(Doctor doctor);
	public void deleteDoctorProfile(Long id);
	public Doctor updateDoctorProfile(Long id, Doctor updateDoctor);
}
