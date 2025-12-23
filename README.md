# 🏥 Hospital Management System Backend API

A **Hospital Management System (HMS)** backend built using **Spring Boot**.  
The application provides **RESTful APIs** to manage **Patients, Doctors, Appointments, and Billing** with clean architecture, validation, pagination, and logging.

---

## ✨ Features

### Patient Management
- Register new patients
- Update patient details
- View patient records
- Delete patient records

### Doctor Management
- Register doctors
- Update doctor profiles
- View doctor details
- Remove doctors

### Appointment Management
- Schedule appointments
- Update appointment details
- View appointment history
- Cancel appointments

### Billing Management
- Generate hospital bills
- Update bill details and status
- View billing records
- Delete bills

### Core Backend Features
- RESTful API design
- Pagination support
- Input validation
- Exception handling
- SLF4J logging
- Clean layered architecture

---

## 🛠️ Tech Stack

- **Language:** Java  
- **Framework:** Spring Boot  
- **Web:** Spring MVC  
- **ORM:** Spring Data JPA (Hibernate)  
- **Database:** MySQL  
- **Build Tool:** Maven  
- **Logging:** SLF4J  
- **Testing:** JUnit, Mockito  
- **Tools:** Git, GitHub, Postman, STS  

---


## 🏗️ Project Architecture

```text
com.nt.hms
│
├── controller
│   ├── PatientController.java
│   ├── DoctorController.java
│   ├── AppointmentController.java
│   └── BillController.java
│
├── service
│   ├── IPatientService.java
│   ├── IDoctorService.java
│   ├── IAppointmentService.java
│   └── IBillService.java
│
├── serviceImpl
│   ├── PatientServiceImpl.java
│   ├── DoctorServiceImpl.java
│   ├── AppointmentServiceImpl.java
│   └── BillServiceImpl.java
│
├── repository
│   ├── PatientRepository.java
│   ├── DoctorRepository.java
│   ├── AppointmentRepository.java
│   └── BillRepository.java
│
├── entity
│   ├── Patient.java
│   ├── Doctor.java
│   ├── Appointment.java
│   └── Bill.java
│
└── HospitalManagementApplication.java
