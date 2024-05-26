package com.example.test_study.user.service;

import com.example.test_study.common.domain.exception.ResourceNotFoundException;
import com.example.test_study.common.service.ClockHolder;
import com.example.test_study.common.service.UuidHolder;
import com.example.test_study.user.controller.port.UserService;
import com.example.test_study.user.domain.User;
import com.example.test_study.user.domain.UserStatus;
import com.example.test_study.user.domain.UserCreate;
import com.example.test_study.user.domain.UserUpdate;
import com.example.test_study.user.service.port.CertificationService;
import com.example.test_study.user.service.port.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Builder
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CertificationService certificationService;
    private final UuidHolder uuidHolder;
    private final ClockHolder clockHolder;

    public User getByEmail(String email) {
        return userRepository.findByEmailAndStatus(email, UserStatus.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("User", email));
    }

    public User getById(long id) {
        return userRepository.findByIdAndStatus(id, UserStatus.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));
    }

    @Transactional
    public User create(UserCreate userCreate) {
        User user = userRepository.save(User.from(userCreate, uuidHolder));
        certificationService.send(
                user.getEmail(),
                user.getId(),
                user.getCertificationCode());
        return user;
    }

    @Transactional
    public User update(long id, UserUpdate userUpdate) {
        User user = getById(id);
        user = user.update(userUpdate);
        user = userRepository.save(user);
        return user;
    }

    public void login(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Users",id));
        user = user.login(clockHolder);
        userRepository.save(user);
    }

    public void verifyEmail(long id, String certificationCode) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Users", id));
        user = user.certification(certificationCode);
        userRepository.save(user);
    }
}
