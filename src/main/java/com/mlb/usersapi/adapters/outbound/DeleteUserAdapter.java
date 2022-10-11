package com.mlb.usersapi.adapters.outbound;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Component;

import com.mlb.usersapi.adapters.inbound.entity.UserEntity;
import com.mlb.usersapi.adapters.outbound.repository.UserRepository;
import com.mlb.usersapi.application.core.exception.NotFoundException;
import com.mlb.usersapi.application.ports.secondary.DeleteUserPort;

import java.util.Optional;

@Component
@AllArgsConstructor
public class DeleteUserAdapter implements DeleteUserPort {

    private final UserRepository userRepository;

    @Override
    public void delete(Long id) {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            throw new NotFoundException("User " + id + " does not exist.");
        } else {
            userRepository.delete(userOptional.get());
        }
    }
}
