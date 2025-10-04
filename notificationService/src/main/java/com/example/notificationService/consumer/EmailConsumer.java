package com.example.notificationService.consumer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.notificationservice.dto.EmailRequest;

@Service
public class EmailConsumer {
	
	@Autowired
	JavaMailSender javaMailSender;
//
//    @KafkaListener(topics = "send_email", groupId = "notification-group")
//	public void consume(EmailRequest emailRequest) {
//		SimpleMailMessage message=new SimpleMailMessage();
//	message.setTo(emailRequest.getTo());
//	message.setSubject(emailRequest.getSubject());
//	message.setText(emailRequest.getBody());
//	
//	javaMailSender.send(message);
//	}
//}
	@Autowired
	private ObjectMapper objectMapper;

	@KafkaListener(topics = "send_email", groupId = "notification-group")
	public void consume(String message) throws JsonProcessingException {
	    EmailRequest request = objectMapper.readValue(message, EmailRequest.class);
	    System.out.println("Received: " + request);
	    System.out.println("Received: " + request.getTo());
	    System.out.println("Received: " + request.getSubject());
	    System.out.println("Received: " + request.getBody());
	    
	    SimpleMailMessage email=new SimpleMailMessage();
	    email.setTo(request.getTo());
	    email.setSubject(request.getSubject());
	    email.setText(request.getBody());
		
	    try {
	        javaMailSender.send(email);
	        System.out.println("Email sent successfully!");
	    } catch (MailException e) {
	        System.err.println("Failed to send email: " + e.getMessage());
	        e.printStackTrace();
	    }

	}
}