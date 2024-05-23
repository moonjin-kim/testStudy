package com.example.test_study.repository;

import com.example.test_study.model.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByIdAndStatus(Long id, UserStatus status);
    Optional<UserEntity> findByEmailAndStatus(String email, UserStatus status);
}
