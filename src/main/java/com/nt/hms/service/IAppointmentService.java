package com.nt.hms.service;


import org.springframework.data.domain.Page;

import com.nt.hms.Entity.Appointment;


public interface IAppointmentService {
	
	public Page<Appointment> getAllAppointments(int page, int size);;
	public Appointment getAppointmentById(Long id);
	public Appointment createAppointment(Appointment appointment);
	public Appointment updateAppointment(Long id, Appointment appointment);
	public void deleteAppointment(Long id);
}