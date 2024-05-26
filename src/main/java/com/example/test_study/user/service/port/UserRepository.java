package com.example.test_study.user.service.port;

import com.example.test_study.user.domain.User;
import com.example.test_study.user.domain.UserStatus;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByEmailAndStatus(String email, UserStatus userStatus);

    Optional<User> findByIdAndStatus(long id, UserStatus userStatus);

    User save(User user);

    Optional<User> findById(long id);
}
