package com.nt.hms.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nt.hms.Entity.Patient;
import com.nt.hms.serviceImpl.PatientServiceImpl;

@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {


	
	@Autowired
	private PatientServiceImpl patientservice;

	
	@GetMapping
	public Page<Patient> getAllPatient(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "2")int size){
		System.out.println("Fetching the patients");
		return patientservice.getAllPatient(page, size);
		
	}
	
	@PostMapping
	public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
		System.out.println("Creating patient ");
		return ResponseEntity.ok(patientservice.createPatientProfile(patient));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
		System.out.println("Fetching patient by Id: "+ id);
		return ResponseEntity.ok(patientservice.getPatientProfileById(id));
	}
	
	@DeleteMapping("/{id}")
	public void deletePatient(@PathVariable Long id) {
		patientservice.deletePatientProfile(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody Patient patient) {
		System.out.println("Update Patient with Id:"+id);
		return ResponseEntity.ok(patientservice.updatePatientProfile(id, patient));
	}
}
