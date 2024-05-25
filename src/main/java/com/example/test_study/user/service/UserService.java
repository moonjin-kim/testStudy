package com.example.test_study.user.service;

import com.example.test_study.common.domain.exception.CertificationCodeNotMatchedException;
import com.example.test_study.common.domain.exception.ResourceNotFoundException;
import com.example.test_study.user.domain.UserStatus;
import com.example.test_study.user.domain.dto.UserCreateDto;
import com.example.test_study.user.domain.dto.UserUpdateDto;
import com.example.test_study.user.repository.UserEntity;
import com.example.test_study.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JavaMailSender mailSender;

    public UserEntity getByEmail(String email) {
        return userRepository.findByEmailAndStatus(email, UserStatus.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("User", email));
    }

    public UserEntity getById(long id) {
        return userRepository.findByIdAndStatus(id, UserStatus.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));
    }

    @Transactional
    public UserEntity create(UserCreateDto userCreateDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(userCreateDto.getEmail());
        userEntity.setNickname(userCreateDto.getNickname());
        userEntity.setAddress(userCreateDto.getAddress());
        userEntity.setStatus(UserStatus.PENDING);
        userEntity.setCertificationCode(UUID.randomUUID().toString());
        userEntity = userRepository.save(userEntity);
        return userEntity;
    }

    @Transactional
    public UserEntity update(long id, UserUpdateDto userUpdateDto) {
        UserEntity userEntity = getById(id);
        userEntity.setNickname(userUpdateDto.getNickname());
        userEntity.setAddress(userUpdateDto.getAddress());
        userEntity = userRepository.save(userEntity);
        return userEntity;
    }

    public void login(long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Users",id));
        userEntity.setLastLoginAt(Clock.systemUTC().millis());
        userRepository.save(userEntity);
    }

    public void verifyEmail(long id, String certificationCode) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Users", id));
        if (!certificationCode.equals(userEntity.getCertificationCode())) {
            throw new CertificationCodeNotMatchedException();
        }
        userEntity.setStatus(UserStatus.ACTIVE);
        userRepository.save(userEntity);
    }

    private void sendCertificationEmail(String email, String certificationUrl) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Please certify your email address");
        message.setText("Please click the following link to certify your email address: " + certificationUrl);
        mailSender.send(message);
    }

    private String generateCertificationUrl(UserEntity userEntity) {
        return "http://localhost:8080/api/v0/users/" + userEntity.getId() + "/verify?certificationCode=" + userEntity.getCertificationCode();
    }
}
