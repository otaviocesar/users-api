package com.mlb.usersapi.application.core.services;

import com.mlb.usersapi.application.core.domain.User;
import com.mlb.usersapi.application.ports.primary.SaveUserPort;

public class SaveUserService implements SaveUserPort {

    private final SaveUserPort saveUserPort;

    public SaveUserService(SaveUserPort saveUserPort) {
        this.saveUserPort = saveUserPort;
    }

    @Override
    public User save(User user) {
        return saveUserPort.save(user);
    }
}
