package com.mlb.usersapi.service;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mlb.usersapi.application.core.domain.User;
import com.mlb.usersapi.application.core.services.FindAllUsersService;
import com.mlb.usersapi.application.ports.secondary.FindAllUsersPort;
import com.mlb.usersapi.util.UserFactory;

@ExtendWith(SpringExtension.class)
class FindAllUsersServiceTest {
    @InjectMocks
    private FindAllUsersService userService;
    @Mock
    private FindAllUsersPort findAllUsersPortMock;

    @BeforeEach
    void setUp(){
        BDDMockito.when(findAllUsersPortMock.findAll("Maria Mary"))
                .thenReturn(List.of(UserFactory.createValidUser()));
    }

    @Test
    @DisplayName("listAll returns list of anime when successful")
    void listAllNonPageable_ReturnsListOfAnimes_WhenSuccessful(){
        String expectedName = UserFactory.createValidUser().getName();

        List<User> users = userService.findAll(expectedName);

        Assertions.assertThat(users)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(users.get(0).getName()).isEqualTo(expectedName);
    }
}
