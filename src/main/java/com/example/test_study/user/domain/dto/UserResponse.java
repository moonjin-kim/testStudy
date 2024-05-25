package com.example.test_study.user.domain.dto;

import com.example.test_study.user.domain.UserStatus;
import com.example.test_study.user.repository.UserEntity;
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

    static public UserResponse toResponse(UserEntity userEntity) {
        return UserResponse.builder()
                .id(userEntity.getId())
                .email(userEntity.getEmail())
                .nickname(userEntity.getNickname())
                .address(userEntity.getAddress())
                .status(userEntity.getStatus())
                .build();
    }
}
