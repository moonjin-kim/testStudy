package com.example.test_study.exception;

public class CertificationCodeNotMatchedException extends RuntimeException {
    public CertificationCodeNotMatchedException(){
        super("잘못된 인증번호 입니다.");
    }
}
