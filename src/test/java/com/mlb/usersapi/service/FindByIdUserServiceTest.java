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
import com.mlb.usersapi.application.core.services.FindByIdUserService;
import com.mlb.usersapi.application.ports.secondary.FindByIdUserPort;
import com.mlb.usersapi.util.UserFactory;

@ExtendWith(SpringExtension.class)
class FindByIdUserServiceTest {
    @InjectMocks
    private FindByIdUserService userService;
    @Mock
    private FindByIdUserPort findByIdUserPortMock;

    @BeforeEach
    void setUp(){
        BDDMockito.when(findByIdUserPortMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(UserFactory.createValidUser()); 
        BDDMockito.when(findByIdUserPortMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(UserFactory.createValidUser());                               
    }

    @Test
    @DisplayName("findById returns user when successful")
    void findById_ReturnsUser_WhenSuccessful(){
        Long expectedId = UserFactory.createValidUser().getId();

        User user = userService.findById(1L);

        Assertions.assertThat(user).isNotNull();

        Assertions.assertThat(user.getId()).isNotNull().isEqualTo(expectedId);
    }
}
