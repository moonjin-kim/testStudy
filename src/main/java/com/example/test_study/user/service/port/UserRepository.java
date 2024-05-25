package com.example.test_study.user.service.port;

import com.example.test_study.user.domain.UserStatus;
import com.example.test_study.user.infrastructure.UserEntity;

import java.util.Optional;

public interface UserRepository {
    Optional<UserEntity> findByEmailAndStatus(String email, UserStatus userStatus);

    Optional<UserEntity> findByIdAndStatus(long id, UserStatus userStatus);

    UserEntity save(UserEntity userEntity);

    Optional<UserEntity> findById(long id);
}
