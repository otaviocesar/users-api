package com.mlb.usersapi.util;

import java.time.LocalDate;
import java.time.Month;

import com.mlb.usersapi.adapters.inbound.entity.UserEntity;

public class UserFactory {

    public static UserEntity createUserToBeSaved(){
        return UserEntity.builder()
                .name("Maria Mary")
                .cpf("91521754055")
                .email("teste@email.com")
                .birthDate(LocalDate.of(1992, Month.SEPTEMBER, 14))
                .build();
    }

    public static UserEntity createValidUser(){
        return UserEntity.builder()
                .name("Maria Mary")
                .cpf("91521754055")
                .birthDate(LocalDate.of(1992, Month.SEPTEMBER, 14))
                .email("teste@email.com")
                .id(1L)
                .build();
    }

    public static UserEntity createValidUpdatedUser(){
        return UserEntity.builder()
                .name("Maria Mary 2")
                .cpf("91521754055")
                .birthDate(LocalDate.of(1992, Month.SEPTEMBER, 14))
                .email("teste@email.com")
                .id(1L)
                .build();
    }

    public static UserEntity createInvalidUser(){
        return UserEntity.builder()
                .name(null)
                .cpf("91521754055")
                .birthDate(LocalDate.of(1992, Month.SEPTEMBER, 14))
                .email("teste@email.com")
                .id(1L)
                .build();
    }
}
