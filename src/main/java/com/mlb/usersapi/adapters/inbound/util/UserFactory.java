package com.mlb.usersapi.adapters.inbound.util;

import com.mlb.usersapi.adapters.inbound.entity.UserEntity;

public class UserFactory {
    public static UserEntity createEmptyUser(){
        return UserEntity.builder()
                .name(null)
                .cpf(null)
                .email(null)
                .birthDate(null)
                .build();
    }
}
