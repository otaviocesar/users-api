package com.mlb.usersapi.application.core.services;

import java.util.List;

import com.mlb.usersapi.application.core.domain.User;
import com.mlb.usersapi.application.ports.primary.FindAllUsersServicePort;
import com.mlb.usersapi.application.ports.secondary.FindAllUsersPort;

public class FindAllUsersService implements FindAllUsersServicePort {

    private final FindAllUsersPort findAllUsersPort;

    public FindAllUsersService(FindAllUsersPort findAllUsersPort) {
        this.findAllUsersPort = findAllUsersPort;
    }

    @Override
    public List<User> findAll() {
        return findAllUsersPort.findAll();
    }
}
