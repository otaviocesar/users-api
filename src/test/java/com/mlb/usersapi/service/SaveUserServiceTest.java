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

import com.mlb.usersapi.application.core.domain.User;
import com.mlb.usersapi.application.core.services.SaveUserService;
import com.mlb.usersapi.application.ports.secondary.SaveUserPort;
import com.mlb.usersapi.util.UserFactory;

@ExtendWith(SpringExtension.class)
class SaveUserServiceTest {
    @InjectMocks
    private SaveUserService userService;
    @Mock
    private SaveUserPort saveUserPortMock;

    @BeforeEach
    void setUp(){
        BDDMockito.when(saveUserPortMock.save(ArgumentMatchers.any(User.class)))
                .thenReturn(UserFactory.createValidUser());
    }

    @Test
    @DisplayName("save returns user when successful")
    void save_ReturnsUser_WhenSuccessful(){

        User user = userService.save(UserFactory.createUserToBeSaved());

        Assertions.assertThat(user.getCpf()).isEqualTo(UserFactory.createUserToBeSaved().getCpf());
        Assertions.assertThat(user.getName()).isEqualTo(UserFactory.createUserToBeSaved().getName());
        Assertions.assertThat(user.getEmail()).isEqualTo(UserFactory.createUserToBeSaved().getEmail());
        Assertions.assertThat(user.getBirthDate()).isEqualTo(UserFactory.createUserToBeSaved().getBirthDate());
        Assertions.assertThat(user.getId()).isNotNull();

    }
}
