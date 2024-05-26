package com.example.test_study.user.controller.port;

import com.example.test_study.user.domain.User;
import com.example.test_study.user.domain.UserCreate;
import com.example.test_study.user.domain.UserUpdate;

public interface UserReadService {
    public User getByEmail(String email);
    public User getById(long id);
}
