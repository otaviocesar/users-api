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
import com.mlb.usersapi.application.core.services.UpdateUserService;
import com.mlb.usersapi.application.ports.secondary.UpdateUserPort;
import com.mlb.usersapi.util.UserFactory;

@ExtendWith(SpringExtension.class)
class UpdateUserServiceTest {
    @InjectMocks
    private UpdateUserService userService;
    @Mock
    private UpdateUserPort updateUserPortMock;

    @BeforeEach
    void setUp(){
        BDDMockito.when(updateUserPortMock.update(ArgumentMatchers.anyLong(), ArgumentMatchers.any(User.class)))
                .thenReturn(UserFactory.createValidUser());
    }

    @Test
    @DisplayName("update returns user when successful")
    void update_ReturnsUser_WhenSuccessful(){

        User user = userService.update(1L, UserFactory.createValidUpdatedUser());

        Assertions.assertThat(user.getCpf()).isEqualTo(UserFactory.createValidUpdatedUser().getCpf());
        Assertions.assertThat(user.getName()).isNotEqualTo(UserFactory.createValidUpdatedUser().getName());
        Assertions.assertThat(user.getEmail()).isEqualTo(UserFactory.createValidUpdatedUser().getEmail());
        Assertions.assertThat(user.getBirthDate()).isEqualTo(UserFactory.createValidUpdatedUser().getBirthDate());
        Assertions.assertThat(user.getId()).isEqualTo(UserFactory.createValidUpdatedUser().getId());
    }
}
