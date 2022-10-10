package com.mlb.usersapi.adapters.inbound.mappers;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.mlb.usersapi.adapters.inbound.entity.UserEntity;
import com.mlb.usersapi.application.core.domain.User;
import com.mlb.usersapi.adapters.inbound.util.UserFactory;

@Component
public class UserToUserEntityMapper {
     public UserEntity mapper(User user){
          var userEntity = UserFactory.createEmptyUser();
          BeanUtils.copyProperties(user, userEntity);
          return userEntity;
     }
}
