package com.example.test_study.user.infrastructure;

import com.example.test_study.user.domain.User;
import com.example.test_study.user.domain.UserStatus;
import com.example.test_study.user.service.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public Optional<User> findByEmailAndStatus(String email, UserStatus userStatus) {
        return userJpaRepository.findByEmailAndStatus(email,userStatus).map(UserEntity::toModel);
    }

    @Override
    public Optional<User> findByIdAndStatus(long id, UserStatus userStatus) {
        return userJpaRepository.findByIdAndStatus(id,userStatus).map(UserEntity::toModel);
    }

    @Override
    public User save(User User) {
        return userJpaRepository.save(UserEntity.fromModel(User)).toModel();
    }

    @Override
    public Optional<User> findById(long id) {
        return userJpaRepository.findById(id).map(UserEntity::toModel);
    }
}
