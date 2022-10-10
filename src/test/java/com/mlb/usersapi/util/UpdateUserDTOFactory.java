package com.mlb.usersapi.util;

import com.mlb.usersapi.adapters.inbound.dtos.UserDTO;

public class UpdateUserDTOFactory {
    public static UserDTO createUserPutRequestBody(){
        return UserDTO.builder()
                .name(UserFactory.createValidUpdatedUser().getName())
                .build();
    }
}