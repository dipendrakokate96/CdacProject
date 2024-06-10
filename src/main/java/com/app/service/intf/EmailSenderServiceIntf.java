package com.app.service.intf;

import javax.validation.Valid;

import com.app.dto.DoctorDTO;

public interface EmailSenderServiceIntf {

	void sendEmailOnAppointmentBooking(Long patientId,String time);
	
	void sendEmailOnCancelAppointment(Long appointmentId);
	
	void sendEmailTokenToResetPassword(String userEmail, Long token);

	void sendEmailToDoctor(@Valid DoctorDTO doctor); 
}
