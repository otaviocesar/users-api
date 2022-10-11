package com.mlb.usersapi.application.core.services;

import com.mlb.usersapi.application.core.domain.User;
import com.mlb.usersapi.application.ports.primary.FindByIdUserServicePort;
import com.mlb.usersapi.application.ports.secondary.FindByIdUserPort;

public class FindByIdUserService implements FindByIdUserServicePort {

    private final FindByIdUserPort findByIdUserPort;

    public FindByIdUserService(FindByIdUserPort findByIdUserPort) {
        this.findByIdUserPort = findByIdUserPort;
    }

    @Override
    public User findById(Long id) {
        return findByIdUserPort.findById(id);
    }
}
