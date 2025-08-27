package com.notification.serviceE;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.demo.constant.AppConstants;
import com.notification.dto.EmailRequest;

@Service
public class EmailRequestListener {

    @KafkaListener(topics = AppConstants.TOPIC, groupId = "group_email", containerFactory = "emailKafkaListenerFactory")
    public void kafkaSubscriberContent(EmailRequest emailRequest) {
        System.out.println("_____________ Msg fetched From Kafka _______________");
        System.out.println("To: " + emailRequest.getTo());
        System.out.println("Subject: " + emailRequest.getSubject());
        System.out.println("Body: " + emailRequest.getBody());

        // You can now add email sending logic here
    }
}
