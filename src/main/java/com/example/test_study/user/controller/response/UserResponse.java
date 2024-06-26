package com.example.test_study.user.controller.response;

import com.example.test_study.user.domain.User;
import com.example.test_study.user.domain.UserStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResponse {
    private final Long id;
    private final String email;
    private final String nickname;
    private final String address;
    private final UserStatus status;

    @Builder
    public UserResponse(
            Long id,
            String email,
            String nickname,
            String address,
            UserStatus status
    ) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.address = address;
        this.status = status;
    }

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .address(user.getAddress())
                .status(user.getStatus())
                .build();
    }
}
