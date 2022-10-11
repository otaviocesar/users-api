package com.mlb.usersapi.adapters.inbound.dtos;
import javax.validation.constraints.Email;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class PatchUserDTO{

    @Length(min = 3, max = 50, message = "{name.length}")
    private String name;

    @Email(message = "{email.isInvalid}")
    private String email;

    @CPF(message = "{cpf.isInvalid}")
    private String cpf;
    
    @Past(message = "{date.isInvalid}")
    private LocalDate birthDate;
}