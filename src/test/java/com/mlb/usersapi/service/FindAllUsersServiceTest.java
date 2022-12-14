package com.mlb.usersapi.service;

import java.util.Collections;
import java.util.List;

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
    @DisplayName("findAll returns list of user when successful")
    void findAll_ReturnsListOfUsers_WhenSuccessful(){
        String expectedName = UserFactory.createValidUser().getName();

        List<User> users = userService.findAll(expectedName);

        Assertions.assertThat(users)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(users.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findAll returns an empty list of user when user is not found")
    void findAll_ReturnsEmptyListOfUser_WhenUserIsNotFound(){
        BDDMockito.when(userService.findAll(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<User> users = userService.findAll("user");

        Assertions.assertThat(users)
                .isNotNull()
                .isEmpty();

    }
}
