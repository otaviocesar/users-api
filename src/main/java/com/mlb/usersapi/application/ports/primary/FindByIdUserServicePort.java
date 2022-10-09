package com.mlb.usersapi.application.ports.primary;

import com.mlb.usersapi.application.core.domain.User;


public interface FindByIdUserServicePort {
    User findById(Long id);
}
