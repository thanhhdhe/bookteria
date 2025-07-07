package com.devteria.profile.service;

import com.devteria.profile.dto.request.EmailRequest;
import com.devteria.profile.dto.request.SendEmailRequest;
import com.devteria.profile.dto.response.EmailResponse;
import com.devteria.profile.exception.AppException;
import com.devteria.profile.exception.ErrorCode;
import com.devteria.profile.repository.httpclient.EmailClient;
import feign.FeignException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailService {
    EmailClient emailClient;
    String apiKey = "Bearer re_ZUxVMRrD_GRzndEKnA7h8H8qmDGbUYVR2";
    public EmailResponse sendEmail(SendEmailRequest sendEmailRequest) {
        EmailRequest emailRequest = EmailRequest.builder()
                .from("onboarding@resend.dev")
                .to(sendEmailRequest.getTo())
                .subject(sendEmailRequest.getSubject())
                .html(sendEmailRequest.getHtml())
                .build();
        try {
            return emailClient.sendEmail(apiKey,emailRequest);
        } catch (FeignException e) {
            throw new AppException(ErrorCode.CANNOT_SEND_EMAIL);
        }
    }
}
