package com.mlb.usersapi.adapters.outbound;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import com.mlb.usersapi.adapters.inbound.mappers.UserEntityToUserMapper;
import com.mlb.usersapi.adapters.inbound.mappers.UserToUserEntityMapper;
import com.mlb.usersapi.adapters.outbound.repository.UserRepository;
import com.mlb.usersapi.application.core.domain.User;
import com.mlb.usersapi.application.core.exception.ConflictException;
import com.mlb.usersapi.application.ports.secondary.SaveUserPort;

import javax.transaction.Transactional;

@Component
@AllArgsConstructor
public class SaveUserAdapter implements SaveUserPort {

    private final UserRepository userRepository;

    private final UserToUserEntityMapper userToUserEntityMapper;

    private final UserEntityToUserMapper userEntityToUserMapper;

    @Override
    @Transactional
    public User save(User user) {
        var userEntity = userToUserEntityMapper.mapper(user);
        if (userRepository.existsByCpf(userEntity.getCpf())){
            throw new ConflictException("User with cpf " + userEntity.getCpf() + " already exists.");
        }
        if (userRepository.existsByEmail(userEntity.getEmail())){
            throw new ConflictException("User with email " + userEntity.getEmail() + " already exists.");
        }
        return userEntityToUserMapper.mapper(userRepository.save(userEntity));
    }
}
