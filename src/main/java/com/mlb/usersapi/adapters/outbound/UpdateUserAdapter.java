package com.mlb.usersapi.adapters.outbound;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Component;

import com.mlb.usersapi.adapters.inbound.entity.UserEntity;
import com.mlb.usersapi.adapters.inbound.mappers.UserEntityToUserMapper;
import com.mlb.usersapi.adapters.inbound.mappers.UserToUserEntityMapper;
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

    private final UserToUserEntityMapper userToUserEntityMapper;

    private final UserEntityToUserMapper userEntityToUserMapper;

    @Override
    @Transactional
    public User update(Long id, User user) {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        Optional<UserEntity> userCpf = userRepository.findByCpf(user.getCpf());
        Optional<UserEntity> userEmail = userRepository.findByEmail(user.getEmail());
        if (!userOptional.isPresent()) {
            throw new NotFoundException("User " + id + " does not exist.");
        }
        var userEntity = userToUserEntityMapper.mapper(user);
        userEntity.setId(userOptional.get().getId());
        if (userCpf.isPresent() && (!userCpf.get().getId().equals(userEntity.getId()))){
            throw new ConflictException("cpf " + userEntity.getCpf() + " is already linked to another record.");
        }
        if (userEmail.isPresent() && (!userEmail.get().getId().equals(userEntity.getId()))){
            throw new ConflictException("email " + userEntity.getEmail() + " is already linked to another record.");
        }
        return userEntityToUserMapper.mapper(userRepository.save(userEntity));
    }
}
