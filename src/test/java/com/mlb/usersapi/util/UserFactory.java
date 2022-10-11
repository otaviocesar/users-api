package com.mlb.usersapi.util;

import java.time.LocalDate;
import java.time.Month;

import com.mlb.usersapi.application.core.domain.User;

public class UserFactory {

    public static User createUserToBeSaved(){
        return User.builder()
                .name("Maria Mary")
                .cpf("91521754055")
                .email("teste@email.com")
                .birthDate(LocalDate.of(1992, Month.SEPTEMBER, 14))
                .build();
    }

    public static User createValidUser(){
        return User.builder()
                .name("Maria Mary")
                .cpf("91521754055")
                .birthDate(LocalDate.of(1992, Month.SEPTEMBER, 14))
                .email("teste@email.com")
                .id(1L)
                .build();
    }

    public static User createValidUpdatedUser(){
        return User.builder()
                .name("Maria Mary 2")
                .cpf("91521754055")
                .birthDate(LocalDate.of(1992, Month.SEPTEMBER, 14))
                .email("teste@email.com")
                .id(1L)
                .build();
    }

    public static User createInvalidUser(){
        return User.builder()
                .name("Maria Mary")
                .cpf("")
                .birthDate(null)
                .email("teste@email.com")
                .id(1L)
                .build();
    }
}
