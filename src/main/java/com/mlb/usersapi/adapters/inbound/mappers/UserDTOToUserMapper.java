package com.mlb.usersapi.adapters.inbound.mappers;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.mlb.usersapi.adapters.inbound.dtos.UserDTO;
import com.mlb.usersapi.application.core.domain.User;

@Component
public class UserDTOToUserMapper {

    public  User mapper(UserDTO userDTO){
        var user = new User();
        BeanUtils.copyProperties(userDTO, user);
        return user;
    }

}