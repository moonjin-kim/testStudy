package com.example.test_study.user.service.port;

public interface MailSender {

    void send(String email, String title, String content);
}
