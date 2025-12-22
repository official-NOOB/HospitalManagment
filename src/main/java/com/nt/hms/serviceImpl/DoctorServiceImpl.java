package com.nt.hms.serviceImpl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nt.hms.Entity.Doctor;
import com.nt.hms.repository.DoctorRepository;
import com.nt.hms.service.IDoctorService;

@Service
public class DoctorServiceImpl implements IDoctorService {

    private static final Logger logger =
            LoggerFactory.getLogger(DoctorServiceImpl.class);

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public Page<Doctor> getDoctorDirectory(int page, int size) {
        try {
            logger.info("Fetching all doctors | page={}, size={}", page, size);
            Pageable pageable = PageRequest.of(page, size);
            return doctorRepository.findAll(pageable);
        } catch (Exception e) {
            logger.error("Error occurred while fetching doctors: {}", e.getMessage());
            return Page.empty();
        }
    }

    @Override
    public Doctor getDoctorProfileById(Long id) {
        try {
            logger.info("Fetching doctor with id={}", id);

            Optional<Doctor> optionalDoctor = doctorRepository.findById(id);

            if (optionalDoctor.isPresent()) {
                return optionalDoctor.get();
            } else {
                logger.warn("Doctor not found with id={}", id);
                return null; // handled safely without NPE
            }

        } catch (Exception e) {
            logger.error("Error occurred while fetching doctor with id={}. Reason: {}", id, e.getMessage());
            return null;
        }
    }

    @Override
    public Doctor createDoctorProfile(Doctor doctor) {
        try {
            logger.info("Creating doctor");

            // Validation using Optional
            Optional.ofNullable(doctor.getName())
                    .filter(name -> !name.isBlank())
                    .orElseThrow(() -> new IllegalArgumentException("Doctor name must not be empty"));

            Optional.ofNullable(doctor.getAge())
                    .filter(age -> age > 0)
                    .orElseThrow(() -> new IllegalArgumentException("Doctor age must be valid"));

            Doctor savedDoctor = doctorRepository.save(doctor);
            logger.info("Doctor created successfully with id={}", savedDoctor.getId());
            return savedDoctor;

        } catch (IllegalArgumentException e) {
            logger.error("Validation failed while creating doctor: {}", e.getMessage());
            throw e;

        } catch (Exception e) {
            logger.error("Error occurred while creating doctor. Reason: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public Doctor updateDoctorProfile(Long id, Doctor updatedDoctor) {
        try {
            logger.info("Updating doctor with id={}", id);

            Optional<Doctor> optionalDoctor = doctorRepository.findById(id);

            if (optionalDoctor.isEmpty()) {
                logger.warn("Doctor not found with id={}", id);
                return null;
            }

            Doctor existingDoctor = optionalDoctor.get();

            Optional.ofNullable(updatedDoctor.getName())
                    .filter(name -> !name.isBlank())
                    .ifPresent(existingDoctor::setName);

            Optional.ofNullable(updatedDoctor.getAge())
                    .filter(age -> age > 0)
                    .ifPresent(existingDoctor::setAge);

            Doctor savedDoctor = doctorRepository.save(existingDoctor);
            logger.info("Doctor updated successfully with id={}", savedDoctor.getId());
            return savedDoctor;

        } catch (Exception e) {
            logger.error("Error occurred while updating doctor with id={}. Reason: {}", id, e.getMessage());
            return null;
        }
    }

    @Override
    public void deleteDoctorProfile(Long id) {
        try {
            logger.info("Deleting doctor with id={}", id);

            Optional<Doctor> optionalDoctor = doctorRepository.findById(id);

            optionalDoctor.ifPresentOrElse(
                    doctor -> {
                        doctorRepository.deleteById(id);
                        logger.info("Doctor deleted successfully with id={}", id);
                    },
                    () -> logger.warn("Doctor not found with id={}", id)
            );

        } catch (Exception e) {
            logger.error("Error occurred while deleting doctor with id={}. Reason: {}", id, e.getMessage());
        }
    }
}
