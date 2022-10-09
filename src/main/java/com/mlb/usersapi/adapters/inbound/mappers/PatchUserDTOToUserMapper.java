package com.mlb.usersapi.adapters.inbound.mappers;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.mlb.usersapi.adapters.inbound.dtos.PatchUserDTO;
import com.mlb.usersapi.application.core.domain.User;

@Component
public class PatchUserDTOToUserMapper {

    public  User mapper(PatchUserDTO patchUserDTO){
        var user = new User();
        BeanUtils.copyProperties(patchUserDTO, user);
        return user;
    }

}