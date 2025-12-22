package com.nt.hms.serviceImpl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nt.hms.Entity.Patient;
import com.nt.hms.repository.PatientRepository;
import com.nt.hms.service.IPatientService;

@Service
public class PatientServiceImpl implements IPatientService {

    private static final Logger logger =
            LoggerFactory.getLogger(PatientServiceImpl.class);

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public Page<Patient> getAllPatient(int page, int size) {
        try {
            logger.info("Fetching all patients | page={}, size={}", page, size);
            Pageable pageable = PageRequest.of(page, size);
            return patientRepository.findAll(pageable);
        } catch (Exception e) {
            logger.error("Error occurred while fetching patients: {}", e.getMessage());
            return Page.empty();
        }
    }

    @Override
    public Patient getPatientProfileById(Long id) {
        try {
            logger.info("Fetching patient with id={}", id);

            Optional<Patient> optionalPatient = patientRepository.findById(id);

            if (optionalPatient.isPresent()) {
                return optionalPatient.get();
            } else {
                logger.warn("Patient not found with id={}", id);
                return null; // safe handling, no NPE
            }

        } catch (Exception e) {
            logger.error("Error occurred while fetching patient with id={}. Reason: {}", id, e.getMessage());
            return null;
        }
    }

    @Override
    public Patient createPatientProfile(Patient patient) {
        try {
            logger.info("Creating patient");

            // Validation using Optional
            Optional.ofNullable(patient.getName())
                    .filter(name -> !name.isBlank())
                    .orElseThrow(() ->
                            new IllegalArgumentException("Patient name must not be empty"));

            Optional.ofNullable(patient.getAge())
                    .filter(age -> age > 0)
                    .orElseThrow(() ->
                            new IllegalArgumentException("Patient age must be valid"));

            Optional.ofNullable(patient.getGender())
                    .filter(gender -> !gender.isBlank())
                    .orElseThrow(() ->
                            new IllegalArgumentException("Patient gender must not be empty"));

            Patient savedPatient = patientRepository.save(patient);
            logger.info("Patient created successfully with id={}", savedPatient.getId());
            return savedPatient;

        } catch (IllegalArgumentException e) {
            logger.error("Validation failed while creating patient: {}", e.getMessage());
            throw e;

        } catch (Exception e) {
            logger.error("Error occurred while creating patient. Reason: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public Patient updatePatientProfile(Long id, Patient updatePatient) {
        try {
            logger.info("Updating patient with id={}", id);

            Optional<Patient> optionalPatient = patientRepository.findById(id);

            if (optionalPatient.isEmpty()) {
                logger.warn("Patient not found with id={}", id);
                return null;
            }

            Patient existingPatient = optionalPatient.get();

            Optional.ofNullable(updatePatient.getName())
                    .filter(name -> !name.isBlank())
                    .ifPresent(existingPatient::setName);

            Optional.ofNullable(updatePatient.getAge())
                    .filter(age -> age > 0)
                    .ifPresent(existingPatient::setAge);

            Optional.ofNullable(updatePatient.getGender())
                    .filter(gender -> !gender.isBlank())
                    .ifPresent(existingPatient::setGender);

            Patient savedPatient = patientRepository.save(existingPatient);
            logger.info("Patient updated successfully with id={}", savedPatient.getId());
            return savedPatient;

        } catch (IllegalArgumentException e) {
            logger.error("Validation failed while updating patient: {}", e.getMessage());
            throw e;

        } catch (Exception e) {
            logger.error("Error occurred while updating patient with id={}. Reason: {}", id, e.getMessage());
            return null;
        }
    }

    @Override
    public void deletePatientProfile(Long id) {
        try {
            logger.info("Deleting patient with id={}", id);

            Optional<Patient> optionalPatient = patientRepository.findById(id);

            optionalPatient.ifPresentOrElse(
                    patient -> {
                        patientRepository.deleteById(id);
                        logger.info("Patient deleted successfully with id={}", id);
                    },
                    () -> logger.warn("Patient not found with id={}", id)
            );

        } catch (Exception e) {
            logger.error("Error occurred while deleting patient with id={}. Reason: {}", id, e.getMessage());
        }
    }
}
