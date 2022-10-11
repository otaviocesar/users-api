package com.mlb.usersapi.application.core.services;

import com.mlb.usersapi.application.ports.primary.DeleteUserServicePort;
import com.mlb.usersapi.application.ports.secondary.DeleteUserPort;

public class DeleteUserService implements DeleteUserServicePort {

    private final DeleteUserPort deleteUserPort;

    public DeleteUserService(DeleteUserPort deleteUserPort) {
        this.deleteUserPort = deleteUserPort;
    }

    @Override
    public void delete(Long id) {
        deleteUserPort.delete(id);
    }
}
