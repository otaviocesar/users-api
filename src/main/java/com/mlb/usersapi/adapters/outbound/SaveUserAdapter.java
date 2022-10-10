package com.mlb.usersapi.adapters.outbound;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import com.mlb.usersapi.adapters.inbound.entity.UserEntity;
import com.mlb.usersapi.adapters.inbound.mappers.UserEntityToUserMapper;
import com.mlb.usersapi.adapters.inbound.mappers.UserToUserEntityMapper;
import com.mlb.usersapi.adapters.outbound.repository.UserRepository;
import com.mlb.usersapi.application.core.domain.User;
import com.mlb.usersapi.application.core.exception.BadRequestException;
import com.mlb.usersapi.application.core.exception.ConflictException;
import com.mlb.usersapi.application.ports.secondary.SaveUserPort;

import java.time.LocalDate;
import java.time.Period;

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
        checkExistsByCpf(userEntity);
        checkExistsByEmail(userEntity);
        checkUserAge(userEntity);
        return userEntityToUserMapper.mapper(userRepository.save(userEntity));
    }

    public void checkExistsByCpf(UserEntity userEntity) {
        if (userRepository.existsByCpf(userEntity.getCpf())){
            throw new ConflictException("User with cpf " + userEntity.getCpf() + " already exists.");
        }
    }

    public void checkExistsByEmail(UserEntity userEntity) {
        if (userRepository.existsByEmail(userEntity.getEmail())){
            throw new ConflictException("User with email " + userEntity.getEmail() + " already exists.");
        }
    }

    public void checkUserAge(UserEntity userEntity) {
        int userAge = Period.between(userEntity.getBirthDate(), LocalDate.now()).getYears();
        if (userAge < 18){
            throw new BadRequestException("User is " + userAge + " years old. Must be over 18 years old.");
        }
    }
}
