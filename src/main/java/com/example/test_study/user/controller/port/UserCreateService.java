package com.example.test_study.user.controller.port;

import com.example.test_study.user.domain.User;
import com.example.test_study.user.domain.UserCreate;

public interface UserCreateService {
    public User create(UserCreate userCreate);
}
