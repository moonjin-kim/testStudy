package com.example.test_study.user.service;

import com.example.test_study.user.infrastructure.UserEntity;
import com.example.test_study.user.service.port.MailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CertificationService {
    private final MailSender mailSender;

    public void send(String email, long userId, String certificationCode) {
        String certificationUrl = generateCertificationUrl(userId, generateCertificationUrl(userId, certificationCode));
        String title = "Please certify your email address";
        String content = "Please click the following link to certify your email address: " + certificationUrl;
        mailSender.send(email, title, content);
    }

    public String generateCertificationUrl( long userId, String certificationUrl) {
        return "http://localhost:8080/api/v0/users/" + userId + "/verify?certificationCode=" + certificationUrl;
    }
}
