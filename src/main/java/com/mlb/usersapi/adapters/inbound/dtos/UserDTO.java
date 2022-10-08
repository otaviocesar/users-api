package com.mlb.usersapi.adapters.inbound.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserDTO{

    private String name;

    private String email;

    private String cpf;
    
    private LocalDate birthDate;
}