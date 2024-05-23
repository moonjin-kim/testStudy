package com.example.test_study.model.dto;

import com.example.test_study.repository.UserEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

public class UserResponse {
    private final Long id;
    private final String email;
    private final String nickname;
    private final String address;

    @Builder
    public UserResponse(
            Long id,
            String email,
            String nickname,
            String address
    ) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.address = address;
    }

    static public UserResponse toResponse(UserEntity userEntity) {
        return UserResponse.builder()
                .id(userEntity.getId())
                .email(userEntity.getEmail())
                .nickname(userEntity.getNickname())
                .address(userEntity.getAddress())
                .build();
    }
}
