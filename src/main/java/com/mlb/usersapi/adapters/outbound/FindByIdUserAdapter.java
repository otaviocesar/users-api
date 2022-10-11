package com.mlb.usersapi.adapters.outbound;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Component;

import com.mlb.usersapi.adapters.inbound.entity.UserEntity;
import com.mlb.usersapi.adapters.inbound.mappers.UserEntityToUserMapper;
import com.mlb.usersapi.adapters.outbound.repository.UserRepository;
import com.mlb.usersapi.application.core.domain.User;
import com.mlb.usersapi.application.core.exception.NotFoundException;
import com.mlb.usersapi.application.ports.secondary.FindByIdUserPort;

import java.util.Optional;

@Component
@AllArgsConstructor
public class FindByIdUserAdapter implements FindByIdUserPort {

    private final UserRepository userRepository;

    private final UserEntityToUserMapper userEntityToUserMapper;

    @Override
    public User findById(Long id) {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            throw new NotFoundException("User " + id + " does not exist.");
        }
        return userEntityToUserMapper.mapper(userOptional.get());
    }
}
