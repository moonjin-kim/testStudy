package com.example.test_study.user.controller.port;

import com.example.test_study.user.domain.User;
import com.example.test_study.user.domain.UserCreate;
import com.example.test_study.user.domain.UserUpdate;

public interface UserService {
    public User getByEmail(String email);
    public User getById(long id);
    public User create(UserCreate userCreate);
    public void login(String email);
    public void verifyEmail(long id, String certificationCode);
    public User update(long id, UserUpdate userUpdate);
}
