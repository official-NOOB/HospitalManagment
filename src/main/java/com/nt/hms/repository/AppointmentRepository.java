package com.nt.hms.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nt.hms.Entity.Appointment;


@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}