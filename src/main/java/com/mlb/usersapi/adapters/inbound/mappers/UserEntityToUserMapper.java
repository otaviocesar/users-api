package com.mlb.usersapi.adapters.inbound.mappers;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.mlb.usersapi.adapters.inbound.entity.UserEntity;
import com.mlb.usersapi.application.core.domain.User;

@Component
public class UserEntityToUserMapper {

     public User mapper(UserEntity userEntity){
          var user = new User();
          BeanUtils.copyProperties(userEntity, user);
          return user;
     }

}