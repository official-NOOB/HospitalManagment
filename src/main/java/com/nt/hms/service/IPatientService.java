package com.nt.hms.service;

import org.springframework.data.domain.Page;

import com.nt.hms.Entity.Patient;

public interface IPatientService {
	public Page<Patient> getAllPatient(int page, int size);
	public Patient getPatientProfileById(Long id);
	public Patient createPatientProfile(Patient patient);
	public void deletePatientProfile(Long id);
	public Patient updatePatientProfile(Long id, Patient updatePatient);
}
