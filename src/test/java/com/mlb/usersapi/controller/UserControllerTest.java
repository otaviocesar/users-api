package com.mlb.usersapi.controller;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mlb.usersapi.adapters.inbound.UserController;
import com.mlb.usersapi.adapters.inbound.dtos.PatchUserDTO;
import com.mlb.usersapi.adapters.inbound.dtos.UserDTO;
import com.mlb.usersapi.adapters.inbound.mappers.PatchUserDTOToUserMapper;
import com.mlb.usersapi.adapters.inbound.mappers.UserDTOToUserMapper;
import com.mlb.usersapi.application.core.domain.User;
import com.mlb.usersapi.application.ports.primary.DeleteUserServicePort;
import com.mlb.usersapi.application.ports.primary.FindAllUsersServicePort;
import com.mlb.usersapi.application.ports.primary.FindByIdUserServicePort;
import com.mlb.usersapi.application.ports.primary.SaveUserServicePort;
import com.mlb.usersapi.application.ports.primary.UpdateUserServicePort;
import com.mlb.usersapi.util.PatchUserDTOFactory;
import com.mlb.usersapi.util.UpdateUserDTOFactory;
import com.mlb.usersapi.util.UserDTOFactory;
import com.mlb.usersapi.util.UserFactory;

import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
class UserControllerTest {
    @InjectMocks
    private UserController userController;
    @Mock
    private SaveUserServicePort saveUserServiceMock;
    @Mock
    private UpdateUserServicePort updateUserServiceMock;
    @Mock
    private DeleteUserServicePort deleteUserServiceMock;
    @Mock
    private FindAllUsersServicePort findAllUsersServiceMock;
    @Mock
    private FindByIdUserServicePort findByIdUserServiceMock;
    @Mock
    private UserDTOToUserMapper userDTOToUserMapperMock;
    @Mock
    private PatchUserDTOToUserMapper patchUserDTOToUserMapper;

    @BeforeEach
    void setUp(){
        BDDMockito.when(findAllUsersServiceMock.findAll("Maria Mary"))
                .thenReturn(List.of(UserFactory.createValidUser()));

        BDDMockito.when(findByIdUserServiceMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(UserFactory.createValidUser());

        BDDMockito.when(findAllUsersServiceMock.findAll(ArgumentMatchers.anyString()))
                .thenReturn(List.of(UserFactory.createValidUser()));

        BDDMockito.when(userDTOToUserMapperMock.mapper(ArgumentMatchers.any(UserDTO.class)))
                .thenReturn(UserFactory.createValidUser());

        BDDMockito.when(saveUserServiceMock.save(ArgumentMatchers.any(User.class)))
                .thenReturn(UserFactory.createValidUser());

        BDDMockito.when(updateUserServiceMock.update(ArgumentMatchers.anyLong(), ArgumentMatchers.any(User.class)))
                .thenReturn(UserFactory.createValidUser());  
                
        BDDMockito.when(patchUserDTOToUserMapper.mapper(ArgumentMatchers.any(PatchUserDTO.class)))
                .thenReturn(UserFactory.createValidUser());
    }

    @Test
    @DisplayName("findAll returns list of user when successful")
    void findAll_ReturnsListOfUsers_WhenSuccessful(){
        String expectedName = UserFactory.createValidUser().getName();

        List<User> users = userController.getAll("Maria Mary").getBody();

        Assertions.assertThat(users)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(users.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findById returns user when successful")
    void findById_ReturnsUser_WhenSuccessful(){
        Long expectedId = UserFactory.createValidUser().getId();

        User user = userController.findById(1L).getBody();

        Assertions.assertThat(user).isNotNull();

        Assertions.assertThat(user.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByName returns a list of user when successful")
    void findByName_ReturnsListOfUser_WhenSuccessful(){
        String expectedName = UserFactory.createValidUser().getName();

        List<User> users = userController.getAll("user").getBody();

        Assertions.assertThat(users)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(users.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByName returns an empty list of user when user is not found")
    void findByName_ReturnsEmptyListOfUser_WhenUserIsNotFound(){
        BDDMockito.when(findAllUsersServiceMock.findAll(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<User> users = userController.getAll("user").getBody();

        Assertions.assertThat(users)
                .isNotNull()
                .isEmpty();

    }

    @Test
    @DisplayName("save returns user when successful")
    void save_ReturnsUser_WhenSuccessful(){

        User user = userController.save(UserDTOFactory.createUserPostRequestBody()).getBody();

        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(user.getId()).isNotNull();
        Assertions.assertThat(user.getCpf()).isEqualTo(UserDTOFactory.createUserPostRequestBody().getCpf());
        Assertions.assertThat(user.getEmail()).isEqualTo(UserDTOFactory.createUserPostRequestBody().getEmail());
        Assertions.assertThat(user.getName()).isEqualTo(UserDTOFactory.createUserPostRequestBody().getName());

    }

    @Test
    @DisplayName("updates user when successful")
    void updatesUser_WhenSuccessful(){

        Assertions.assertThatCode(() ->userController.update(1L, UpdateUserDTOFactory.createUserPutRequestBody()))
                .doesNotThrowAnyException();

        ResponseEntity<User> entity = userController.update(1L, UpdateUserDTOFactory.createUserPutRequestBody());

        Assertions.assertThat(entity).isNotNull();

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("patch user when successful")
    void patchUser_WhenSuccessful(){

        Assertions.assertThatCode(() ->userController.patch(1L, PatchUserDTOFactory.createUserPutRequestBody()))
                .doesNotThrowAnyException();

        ResponseEntity<User> entity = userController.patch(1L, PatchUserDTOFactory.createUserPutRequestBody());

        Assertions.assertThat(entity).isNotNull();

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("delete removes user when successful")
    void delete_RemovesUser_WhenSuccessful(){

        Assertions.assertThatCode(() ->userController.delete(1L))
                .doesNotThrowAnyException();

        ResponseEntity<Object> entity = userController.delete(1L);

        Assertions.assertThat(entity).isNotNull();

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}