package com.mlb.usersapi.application.ports.secondary;

import com.mlb.usersapi.application.core.domain.User;

public interface FindByIdUserPort {
    User findById(Long id);
}
