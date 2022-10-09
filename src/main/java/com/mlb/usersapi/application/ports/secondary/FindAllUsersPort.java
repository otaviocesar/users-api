package com.mlb.usersapi.application.ports.secondary;

import java.util.List;

import com.mlb.usersapi.application.core.domain.User;

public interface FindAllUsersPort {
    List<User> findAll();
}
