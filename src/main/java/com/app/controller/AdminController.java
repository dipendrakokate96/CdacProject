package com.app.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.DoctorDTO;

import com.app.entity.modal.Doctor;
import com.app.entity.modal.Patient;

import com.app.service.intf.DoctorServiceIntf;
import com.app.service.intf.EmailSenderServiceIntf;
import com.app.service.intf.PatientServiceIntf;

@RestController
@RequestMapping("/admin") //localhost:7777/admin
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdminController {

	// dependencies added in constructor by @Autowired
	private DoctorServiceIntf doctorService;

	private PatientServiceIntf patientService;

	private EmailSenderServiceIntf emailSenderService;

	@Autowired // constructor level autowiring
	public AdminController(DoctorServiceIntf doctorService, PatientServiceIntf patientService,EmailSenderServiceIntf emailSenderService) {
		this.doctorService = doctorService;
		
		this.patientService = patientService;
		
		this.emailSenderService = emailSenderService;
	}

	@PostMapping("/doctorSignUp")
	public ResponseEntity<?> saveDoctor(@RequestBody @Valid DoctorDTO doctor) {
		System.out.println("Doc DTO from ctrler : "+doctor);
		
		Doctor d = doctorService.saveDoctor(doctor);
		if(d == null) {
			System.out.println("BAD REQ IF BLOCK");
			
			return ResponseEntity.badRequest().body(null);
		}
		
		emailSenderService.sendEmailToDoctor(doctor);
		return new ResponseEntity<>(d, HttpStatus.CREATED);
	}

	@GetMapping("/getAllDoctors")
	public List<Doctor> getAllDoctorDetails() {
		return doctorService.getAllDoctors();
	}

	@DeleteMapping("/removeDoctor/{doctorId}")
	public String deleteDoctor(@PathVariable Long doctorId) {
		return doctorService.deleteDoctorById(doctorId);
	}

	@GetMapping("/getAllPatients")
	public List<Patient> getAllPatientDetails() {
		return patientService.getAllPatients();
		
	}
	
//	@GetMapping("/getAllPatients")
//	public String getAllPatientDetails() {
//		//return patientService.getAllPatients();
//		return "gg well played";
//	}

	@DeleteMapping("/removePatient/{patientId}")
	public String deletePatient(@PathVariable Long patientId) {
		return patientService.deletePatientById(patientId);
	}

	

	

}
