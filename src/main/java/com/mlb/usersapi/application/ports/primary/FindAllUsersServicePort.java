package com.mlb.usersapi.application.ports.primary;

import java.util.List;

import com.mlb.usersapi.application.core.domain.User;


public interface FindAllUsersServicePort {
    List<User> findAll();
}
