package com.mlb.usersapi.adapters.outbound.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mlb.usersapi.adapters.inbound.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
