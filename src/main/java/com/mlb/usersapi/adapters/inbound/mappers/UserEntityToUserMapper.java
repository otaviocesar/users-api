package com.mlb.usersapi.adapters.inbound.mappers;

import java.util.ArrayList;
import java.util.List;

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

     public List<User> listMapper(List<UserEntity> usersList) {
          List<User> users = new ArrayList<User>();
          for (UserEntity userEntity : usersList){
               users.add(mapper(userEntity));
          }
          return users;
     }
}