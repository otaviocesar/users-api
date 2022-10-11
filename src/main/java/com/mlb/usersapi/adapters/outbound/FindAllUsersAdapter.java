package com.mlb.usersapi.adapters.outbound;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Component;

import com.mlb.usersapi.adapters.inbound.mappers.UserEntityToUserMapper;
import com.mlb.usersapi.adapters.outbound.repository.UserRepository;
import com.mlb.usersapi.application.core.domain.User;
import com.mlb.usersapi.application.ports.secondary.FindAllUsersPort;

import java.util.List;

@Component
@AllArgsConstructor
public class FindAllUsersAdapter implements FindAllUsersPort {

    private final UserRepository userRepository;

    private final UserEntityToUserMapper userEntityToUserMapper;

    @Override
    public List<User> findAll(String name) {
        if(name != null) {
            return userEntityToUserMapper.listMapper(userRepository.findByName(name));
        } else{
            return userEntityToUserMapper.listMapper(userRepository.findAll());
        }
    }
}
