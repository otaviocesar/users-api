package com.mlb.usersapi.adapters.inbound.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class UserEntity{
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "increment")
    private Long id;

    private String name;

    private String email;

    @Column(nullable = false, unique = true, length = 11)
    private String cpf;
    
    private LocalDate birthDate;
}