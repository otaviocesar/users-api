package com.mlb.usersapi.util;

import com.mlb.usersapi.adapters.inbound.dtos.PatchUserDTO;

public class PatchUserDTOFactory {
    public static PatchUserDTO createUserPutRequestBody(){
        return PatchUserDTO.builder()
                .name(UserFactory.createValidUpdatedUser().getName())
                .build();
    }
}