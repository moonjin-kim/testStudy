package com.example.test_study.user.service.port;

public interface CertificationService {
    public void send(String email, long userId, String certificationCode);
    public String generateCertificationUrl( long userId, String certificationUrl);
}
