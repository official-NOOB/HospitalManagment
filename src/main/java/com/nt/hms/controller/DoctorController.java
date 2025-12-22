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

import com.nt.hms.Entity.Doctor;
import com.nt.hms.serviceImpl.DoctorServiceImpl;

@RestController
@RequestMapping("/api/v1/doctors")
public class DoctorController {
	
	@Autowired
	private DoctorServiceImpl doctorService; 
	
	@GetMapping
	public Page<Doctor> getAllDoctors(@RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "3")int size){
		System.out.println("Fetching the doctors");
		return doctorService.getDoctorDirectory(page, size);
		
	}
	
	@PostMapping
	public ResponseEntity<Doctor> createDoctor(@RequestBody Doctor doctor) {
		System.out.println("Creating doctor ");
		return ResponseEntity.ok(doctorService.createDoctorProfile(doctor));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) {
		System.out.println("Fetching doctor by ID: "+id);
		return ResponseEntity.ok(doctorService.getDoctorProfileById(id));
	}
	
	@DeleteMapping("/{id}")
	public void deleteDoctor(@PathVariable Long id) {
		System.out.println("Deleting doctor with id: "+id);
		doctorService.deleteDoctorProfile(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Doctor> updateDoctor(@PathVariable Long id, @RequestBody Doctor doctor) {
		System.out.println("Updating doctor with id: "+id);
		return ResponseEntity.ok(doctorService.updateDoctorProfile(id, doctor));
	}
}
