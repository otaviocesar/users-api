package com.mlb.usersapi.application.ports.primary;

import com.mlb.usersapi.application.core.domain.User;

public interface UpdateUserServicePort {
    User update(Long id, User user);
}
