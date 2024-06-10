package com.app.service.impl;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.custome_exception.UserHandlingException;
import com.app.dto.DoctorDTO;
import com.app.entity.modal.Appointment;
import com.app.entity.modal.Doctor;
import com.app.entity.modal.Patient;
import com.app.repository.AppointmentRepository;
import com.app.repository.DoctorRepository;
import com.app.service.intf.EmailSenderServiceIntf;
import com.app.service.intf.PatientServiceIntf;

@Service
@Transactional
public class EmailSenderService implements EmailSenderServiceIntf{

	@Autowired
	private PatientServiceIntf patientService;
	
	@Autowired
	private AppointmentRepository appointmentRepo;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private DoctorRepository doctor;
	
	public void sendSimpleEmail(String toEmail, String body, String subject) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("ehealthcare.cdacblr@gmail.com");
		message.setTo(toEmail);
		message.setText(body);
		message.setSubject(subject);
		
		mailSender.send(message);
		System.out.println("message send....");
		
	}
	
	@Override
	public void sendEmailOnAppointmentBooking(Long patientId,String time) {
		
		
		System.out.println(time);
		 DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		 
	        // Convert String to LocalDateTime using Parse() method
	        LocalDateTime localTime = LocalDateTime.parse(time,dateTimeFormatter);
		
		Appointment app= appointmentRepo.findByPatientIdAndAppointmentTime(patientId,localTime);
		
		Doctor dr = app.getDoctor();
		
		Patient patient = patientService.getPatientDetails(patientId);
		sendSimpleEmail(patient.getEmail(), 
				"Appointment Confirmation and You can join on following Link : "+dr.getLink(),
				"Your appointment has been booked at "+time
				);
	}
	
	@Override
	public void sendEmailTokenToResetPassword(String userEmail, Long token) {
		sendSimpleEmail(userEmail, 
				"Token to reset your password : "+token,
				"Reset Password");
	}
	
	@Override
	public void sendEmailOnCancelAppointment(Long appointmentId) {
		
		System.out.println("in mail service "+appointmentId);
	
		Appointment appointment = appointmentRepo.findById(appointmentId).orElseThrow(() -> new UserHandlingException("Invalid Appointment id!!!"));
		Doctor doctor = appointment.getDoctor();
		Patient patient = appointment.getPatient();
		
		sendSimpleEmail(patient.getEmail(), 
				"Your appointment has been cancelled with doctor : "+doctor.getFirstName(),
				"Appointment Cancelled");
		
		sendSimpleEmail(doctor.getEmail(), 
				"Appointment with patient : "+patient.getFirstName()+" has been cancelled",
				"Appointment Cancelled");
	}

	@Override
	public void sendEmailToDoctor(@Valid DoctorDTO doctor) {
		
		sendSimpleEmail(doctor.getEmail(),"your id : " + doctor.getEmail() +" and password : "+doctor.getPassword(),"Onboarding");
		
	}
	
	
	
}
