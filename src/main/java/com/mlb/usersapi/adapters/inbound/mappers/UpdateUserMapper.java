package com.mlb.usersapi.adapters.inbound.mappers;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.mlb.usersapi.adapters.inbound.entity.UserEntity;
import com.mlb.usersapi.application.core.domain.User;

@Component
public class UpdateUserMapper {

    public  UserEntity mapper(Optional<UserEntity> userEntity, User user){
        if (user.getCpf() != null && !userEntity.get().getCpf().equals(user.getCpf())){
            userEntity.get().setCpf(user.getCpf());
        }
        if (user.getEmail() != null && !userEntity.get().getEmail().equals(user.getEmail())){
            userEntity.get().setEmail(user.getEmail());
        }
        if (user.getName() != null && !userEntity.get().getName().equals(user.getName())){
            userEntity.get().setName(user.getName());
        }
        if (user.getBirthDate() != null && !userEntity.get().getBirthDate().equals(user.getBirthDate())){
            userEntity.get().setBirthDate(user.getBirthDate());
        }
        return userEntity.get();
    }

}