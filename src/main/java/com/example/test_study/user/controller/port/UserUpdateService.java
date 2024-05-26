package com.example.test_study.user.controller.port;

import com.example.test_study.user.domain.User;
import com.example.test_study.user.domain.UserUpdate;

public interface UserUpdateService {
    public User update(long id, UserUpdate userUpdate);
}
