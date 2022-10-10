package com.mlb.usersapi.application.core.domain;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder()
@AllArgsConstructor()
@NoArgsConstructor
@Setter()
@Getter()
public class User {

    private Long id;

    private String name;

    private String email;

    private String cpf;

    private LocalDate birthDate;
}
