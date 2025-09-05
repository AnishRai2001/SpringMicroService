//package com.example.demo.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//import com.example.demo.constant.AppConstants;
//import com.example.demo.dto.EmailRequest;
//
//@Service
//public class EmailProducer {
//
//    @Autowired
//    private KafkaTemplate<String, EmailRequest> kafkaTemplate;
//
//    public void sendEmail(EmailRequest request) {
//        kafkaTemplate.send(AppConstants.TOPIC, request);
//    }
//}
