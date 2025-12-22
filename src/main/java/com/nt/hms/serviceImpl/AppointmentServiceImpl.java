package com.nt.hms.serviceImpl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nt.hms.Entity.Appointment;
import com.nt.hms.repository.AppointmentRepository;
import com.nt.hms.service.IAppointmentService;

@Service
public class AppointmentServiceImpl implements IAppointmentService {

    private static final Logger logger =
            LoggerFactory.getLogger(AppointmentServiceImpl.class);

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public Page<Appointment> getAllAppointments(int page, int size) {
        try {
            logger.info("Fetching all appointments | page={}, size={}", page, size);
            Pageable pageable = PageRequest.of(page, size);
            return appointmentRepository.findAll(pageable);
        } catch (Exception e) {
            logger.error("Error occurred while fetching appointments: {}", e.getMessage());
            return Page.empty();
        }
    }

    @Override
    public Appointment getAppointmentById(Long id) {
        try {
            logger.info("Fetching appointment with id={}", id);

            Optional<Appointment> optionalAppointment = appointmentRepository.findById(id);

            if (optionalAppointment.isPresent()) {
                return optionalAppointment.get();
            } else {
                logger.warn("Appointment not found with id={}", id);
                return null; // avoids NPE safely
            }

        } catch (Exception e) {
            logger.error("Error occurred while fetching appointment with id={}. Reason: {}", id, e.getMessage());
            return null;
        }
    }

    @Override
    public Appointment createAppointment(Appointment appointment) {
        try {
            logger.info("Creating appointment");

            // Validation using Optional
            Optional.ofNullable(appointment.getPatientId())
                    .orElseThrow(() ->
                            new IllegalArgumentException("Patient ID must not be null"));

            Optional.ofNullable(appointment.getDoctorId())
                    .orElseThrow(() ->
                            new IllegalArgumentException("Doctor ID must not be null"));

            Optional.ofNullable(appointment.getAppointmentDate())
                    .orElseThrow(() ->
                            new IllegalArgumentException("Appointment date must not be null"));

            Appointment savedAppointment = appointmentRepository.save(appointment);
            logger.info("Appointment created successfully with id={}", savedAppointment.getId());
            return savedAppointment;

        } catch (IllegalArgumentException e) {
            logger.error("Validation failed while creating appointment: {}", e.getMessage());
            throw e;

        } catch (Exception e) {
            logger.error("Error occurred while creating appointment. Reason: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public Appointment updateAppointment(Long id, Appointment updatedAppointment) {
        try {
            logger.info("Updating appointment with id={}", id);

            Optional<Appointment> optionalAppointment = appointmentRepository.findById(id);

            if (optionalAppointment.isEmpty()) {
                logger.warn("Appointment not found with id={}", id);
                return null;
            }

            Appointment existing = optionalAppointment.get();

            Optional.ofNullable(updatedAppointment.getPatientId())
                    .ifPresent(existing::setPatientId);

            Optional.ofNullable(updatedAppointment.getDoctorId())
                    .ifPresent(existing::setDoctorId);

            Optional.ofNullable(updatedAppointment.getAppointmentDate())
                    .ifPresent(existing::setAppointmentDate);

            Appointment savedAppointment = appointmentRepository.save(existing);
            logger.info("Appointment updated successfully with id={}", savedAppointment.getId());
            return savedAppointment;

        } catch (IllegalArgumentException e) {
            logger.error("Validation failed while updating appointment: {}", e.getMessage());
            throw e;

        } catch (Exception e) {
            logger.error("Error occurred while updating appointment with id={}. Reason: {}", id, e.getMessage());
            return null;
        }
    }

    @Override
    public void deleteAppointment(Long id) {
        try {
            logger.info("Deleting appointment with id={}", id);

            Optional<Appointment> optionalAppointment = appointmentRepository.findById(id);

            optionalAppointment.ifPresentOrElse(
                    appointment -> {
                        appointmentRepository.deleteById(id);
                        logger.info("Appointment deleted successfully with id={}", id);
                    },
                    () -> logger.warn("Appointment not found with id={}", id)
            );

        } catch (Exception e) {
            logger.error("Error occurred while deleting appointment with id={}. Reason: {}", id, e.getMessage());
        }
    }
}
