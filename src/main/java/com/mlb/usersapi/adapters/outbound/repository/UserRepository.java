package com.mlb.usersapi.adapters.outbound.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mlb.usersapi.adapters.inbound.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
    Optional<UserEntity> findByCpf(String cpf);
    Optional<UserEntity> findByEmail(String email);
    
    @Query("Select u from UserEntity u where u.name like %:name%")
    List<UserEntity> findByName(@Param("name")String name);
}
