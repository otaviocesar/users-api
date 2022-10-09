package com.mlb.usersapi.adapters.outbound;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Component;

import com.mlb.usersapi.adapters.inbound.entity.UserEntity;
import com.mlb.usersapi.adapters.inbound.mappers.UserEntityToUserMapper;
import com.mlb.usersapi.adapters.inbound.mappers.UpdateUserMapper;
import com.mlb.usersapi.adapters.outbound.repository.UserRepository;
import com.mlb.usersapi.application.core.domain.User;
import com.mlb.usersapi.application.core.exception.ConflictException;
import com.mlb.usersapi.application.core.exception.NotFoundException;
import com.mlb.usersapi.application.ports.secondary.UpdateUserPort;

import java.util.Optional;

import javax.transaction.Transactional;

@Component
@AllArgsConstructor
public class UpdateUserAdapter implements UpdateUserPort {

    private final UserRepository userRepository;

    private final UserEntityToUserMapper userEntityToUserMapper;

    private final UpdateUserMapper updateUserMapper;

    @Override
    @Transactional
    public User update(Long id, User user) {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            throw new NotFoundException("User " + id + " does not exist.");
        }
        checkCPFLinkedToAnotherUser(id, user);
        checkEmailLinkedToAnotherUser(id, user);

        return userEntityToUserMapper.mapper(userRepository.save(updateUserMapper.mapper(userOptional, user)));
    }

    public void checkCPFLinkedToAnotherUser(Long id, User user) {
        Optional<UserEntity> userCpf = userRepository.findByCpf(user.getCpf());
        if (userCpf.isPresent() && (!userCpf.get().getId().equals(id))){
            throw new ConflictException("cpf " + user.getCpf() + " is already linked to another record.");
        }
    }

    public void checkEmailLinkedToAnotherUser(Long id, User user) {
        Optional<UserEntity> userEmail = userRepository.findByEmail(user.getEmail());
        if (userEmail.isPresent() && (!userEmail.get().getId().equals(id))){
            throw new ConflictException("email " + user.getEmail() + " is already linked to another record.");
        }
    }
}
