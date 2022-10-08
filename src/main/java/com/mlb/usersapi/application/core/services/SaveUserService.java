package com.mlb.usersapi.application.core.services;

import com.mlb.usersapi.application.core.domain.User;
import com.mlb.usersapi.application.ports.primary.SaveUserServicePort;

public class SaveUserService implements SaveUserServicePort {

    private final SaveUserServicePort saveUserServicePort;

    public SaveUserService(SaveUserServicePort saveUserServicePort) {
        this.saveUserServicePort = saveUserServicePort;
    }

    @Override
    public User save(User user) {
        return saveUserServicePort.save(user);
    }
}
