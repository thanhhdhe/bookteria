package com.devteria.profile.controller;

import com.devteria.event.dto.NotificationEvent;
import com.devteria.profile.dto.request.SendEmailRequest;
import com.devteria.profile.service.EmailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationController {
    EmailService emailService;

    @KafkaListener(topics = "notification-delivery")
    public void listenNotificationDelivery(NotificationEvent message) {
        log.info("listenNotificationDelivery received : {}", message);
        emailService.sendEmail(SendEmailRequest.builder()
                        .to(message.getRecipient())
                        .subject(message.getSubject())
                        .html(message.getBody())
                .build());
    }

}
