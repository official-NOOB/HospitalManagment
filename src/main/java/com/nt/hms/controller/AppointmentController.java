package com.nt.hms.controller;

import java.util.HashMap;
import java.util.Map;

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

import com.nt.hms.Entity.Appointment;
import com.nt.hms.service.IAppointmentService;
import com.nt.hms.serviceImpl.WebhookServiceImpl;

@RestController
@RequestMapping("/api/v1/appointments")
public class AppointmentController {

	@Autowired
	private IAppointmentService appointmentService;

	@Autowired
	private WebhookServiceImpl webhookService;

	@GetMapping
    public Page<Appointment> getAllAppointments(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {

        return appointmentService.getAllAppointments(page, size);
    }

	@PostMapping
	public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointment) {
		Appointment savedAppointment = appointmentService.createAppointment(appointment);

		Map<String, Object> payload = new HashMap<>();
		payload.put("appointmentId", savedAppointment.getId());
		payload.put("patientId", savedAppointment.getPatientId());
		payload.put("doctorId", savedAppointment.getDoctorId());
		payload.put("appointmentDate", savedAppointment.getAppointmentDate());

		webhookService.sendWebhook("http://localhost:8086/webhook", payload);

		return ResponseEntity.ok(savedAppointment);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long id) {
		return ResponseEntity.ok(appointmentService.getAppointmentById(id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Appointment> updateAppointment(@PathVariable Long id, @RequestBody Appointment appointment) {
		return ResponseEntity.ok(appointmentService.updateAppointment(id, appointment));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
		appointmentService.deleteAppointment(id);
		return ResponseEntity.noContent().build();
	}
}