package com.mlb.usersapi.util;

import com.mlb.usersapi.adapters.inbound.dtos.UserDTO;

public class UserDTOFactory {
    public static UserDTO createUserPostRequestBody(){
        return UserDTO.builder()
                .name(UserEntityFactory.createUserToBeSaved().getName())
                .cpf(UserEntityFactory.createUserToBeSaved().getCpf())
                .email(UserEntityFactory.createUserToBeSaved().getEmail())
                .birthDate(UserEntityFactory.createUserToBeSaved().getBirthDate())
                .build();
    }
}