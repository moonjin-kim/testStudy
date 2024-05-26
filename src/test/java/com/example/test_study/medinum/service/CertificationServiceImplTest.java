package com.example.test_study.medinum.service;

import com.example.test_study.mock.FakeMailSender;
import com.example.test_study.user.service.CertificationService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CertificationServiceImplTest {
    @Test
    public void 이메일과_컨텐츠가_제대로_만들어져서_보내지는지_테스트한다() {
        //given
        FakeMailSender fakeMailSender = new FakeMailSender();
        CertificationService certificationService = new CertificationService(fakeMailSender);

        //when
        certificationService.send("kok202@naver.com", 1, "aaaaaaaa-aaaa-aaaa-aaaaaaaa1aaa");

        //then
        assertThat(fakeMailSender.email).isEqualTo("kok202@naver.com");
        assertThat(fakeMailSender.title).isEqualTo("Please certify your email address");
        assertThat(fakeMailSender.content).isEqualTo("Please click the following link to certify your email address: http://localhost:8080/api/v0/users/1/verify?certificationCode=http://localhost:8080/api/v0/users/1/verify?certificationCode=aaaaaaaa-aaaa-aaaa-aaaaaaaa1aaa");
    }
}