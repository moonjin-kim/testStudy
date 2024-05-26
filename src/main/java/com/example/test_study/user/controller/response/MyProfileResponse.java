package com.example.test_study.user.controller.response;

import com.example.test_study.user.domain.User;
import com.example.test_study.user.domain.UserStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MyProfileResponse {
    private final Long id;
    private final String email;
    private final String nickname;
    private final String address;
    private final UserStatus status;
    private final Long lastLogin;

    @Builder
    public MyProfileResponse(
            Long id,
            String email,
            String nickname,
            String address,
            UserStatus status,
            Long lastLogin
    ) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.address = address;
        this.status = status;
        this.lastLogin = lastLogin;
    }

    public static MyProfileResponse from(User user) {
        return MyProfileResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .address(user.getAddress())
                .status(user.getStatus())
                .lastLogin(user.getLastLoginAt())
                .build();
    }
}
