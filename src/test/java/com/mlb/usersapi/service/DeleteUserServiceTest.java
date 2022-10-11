package com.mlb.usersapi.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mlb.usersapi.application.core.services.DeleteUserService;
import com.mlb.usersapi.application.ports.secondary.DeleteUserPort;

@ExtendWith(SpringExtension.class)
class DeleteUserServiceTest {
    @InjectMocks
    private DeleteUserService userService;
    @Mock
    private DeleteUserPort deleteUserPortMock;

    @BeforeEach
    void setUp(){
        BDDMockito.doNothing().when(deleteUserPortMock).delete(ArgumentMatchers.anyLong());                           
    }

    @Test
    @DisplayName("delete removes user when successful")
    void delete_RemovesUser_WhenSuccessful(){

        Assertions.assertThatCode(() ->userService.delete(1L))
                .doesNotThrowAnyException();

    }
}
