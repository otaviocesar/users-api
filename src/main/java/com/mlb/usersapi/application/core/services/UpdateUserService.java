package com.mlb.usersapi.application.core.services;

import com.mlb.usersapi.application.core.domain.User;
import com.mlb.usersapi.application.ports.primary.UpdateUserServicePort;
import com.mlb.usersapi.application.ports.secondary.UpdateUserPort;

public class UpdateUserService implements UpdateUserServicePort {

    private final UpdateUserPort updateUserPort;

    public UpdateUserService(UpdateUserPort updateUserPort) {
        this.updateUserPort = updateUserPort;
    }

    @Override
    public User update(Long id, User user) {
        return updateUserPort.update(id, user);
    }
}
