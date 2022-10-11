package com.mlb.usersapi.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import com.mlb.usersapi.adapters.inbound.dtos.UserDTO;
import com.mlb.usersapi.adapters.inbound.entity.UserEntity;
import com.mlb.usersapi.adapters.outbound.repository.UserRepository;
import com.mlb.usersapi.application.core.domain.User;
import com.mlb.usersapi.util.UserDTOFactory;
import com.mlb.usersapi.util.UserEntityFactory;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserControllerIT {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @LocalServerPort
    private int port;
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("findAll returns list of user when successful")
    void findAll_ReturnsListOfUsers_WhenSuccessful() {
        UserEntity savedUser = userRepository.save(UserEntityFactory.createUserToBeSaved());

        String expectedName = savedUser.getName();

        List<User> users = testRestTemplate.exchange("/users", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<User>>() {
                }).getBody();

        Assertions.assertThat(users)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(users.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findById returns user when successful")
    void findById_ReturnsUser_WhenSuccessful() {
        UserEntity savedUser = userRepository.save(UserEntityFactory.createUserToBeSaved());

        Long expectedId = savedUser.getId();

        User user = testRestTemplate.getForObject("/users/{id}", User.class, expectedId);

        Assertions.assertThat(user).isNotNull();

        Assertions.assertThat(user.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findAll by name returns a list of user when successful")
    void findByName_ReturnsListOfUser_WhenSuccessful(){
        UserEntity savedUser = userRepository.save(UserEntityFactory.createUserToBeSaved());

        String expectedName = savedUser.getName();

        String url = String.format("/users?name=%s", expectedName);

        List<User> users = testRestTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<User>>() {
                }).getBody();

        Assertions.assertThat(users)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(users.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findAll by name returns an empty list of user when user is not found")
    void findByName_ReturnsEmptyListOfUser_WhenUserIsNotFound(){
        List<User> users = testRestTemplate.exchange("/users?name=xpto", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<User>>() {
                }).getBody();

        Assertions.assertThat(users)
                .isNotNull()
                .isEmpty();

    }

    @Test
    @DisplayName("save returns user when successful")
    void save_ReturnsUser_WhenSuccessful(){
        UserDTO userPostRequestBody = UserDTOFactory.createUserPostRequestBody();

        ResponseEntity<User> userResponseEntity = testRestTemplate.postForEntity("/users", userPostRequestBody, User.class);

        Assertions.assertThat(userResponseEntity).isNotNull();
        Assertions.assertThat(userResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(userResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(userResponseEntity.getBody().getId()).isNotNull();
    }

    @Test
    @DisplayName("save user with cpf already registered Return Conflict")
    void save_User_Cpf_AlreadyRegistered_ReturnConflict(){
        UserEntity savedUser = userRepository.save(UserEntityFactory.createUserToBeSaved());

        UserDTO userPostRequestBody = UserDTOFactory.createUserPostRequestBody();
        userPostRequestBody.setCpf(savedUser.getCpf());

        ResponseEntity<User> userResponseEntity = testRestTemplate.postForEntity("/users", userPostRequestBody, User.class);

        Assertions.assertThat(userResponseEntity).isNotNull();
        Assertions.assertThat(userResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);

    }

    @Test
    @DisplayName("save user with email already registered Return Conflict")
    void save_User_Email_AlreadyRegistered_ReturnConflict(){
        UserEntity savedUser = userRepository.save(UserEntityFactory.createUserToBeSaved());

        UserDTO userPostRequestBody = UserDTOFactory.createUserPostRequestBody();
        userPostRequestBody.setEmail(savedUser.getEmail());

        ResponseEntity<User> userResponseEntity = testRestTemplate.postForEntity("/users", userPostRequestBody, User.class);

        Assertions.assertThat(userResponseEntity).isNotNull();
        Assertions.assertThat(userResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);

    }

    @Test
    @DisplayName("save user user under 18 Return BadRequest")
    void save_User_Under_18_ReturnBadRequest(){

        UserDTO userPostRequestBody = UserDTOFactory.createUserPostRequestBody();
        userPostRequestBody.setBirthDate(LocalDate.now());

        ResponseEntity<User> userResponseEntity = testRestTemplate.postForEntity("/users", userPostRequestBody, User.class);

        Assertions.assertThat(userResponseEntity).isNotNull();
        Assertions.assertThat(userResponseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("update user when successful")
    void updateUser_WhenSuccessful(){
        UserEntity savedUser = userRepository.save(UserEntityFactory.createUserToBeSaved());

        savedUser.setName("João John");

        ResponseEntity<Void> userResponseEntity = testRestTemplate.exchange("/users/{id}",
                HttpMethod.PUT,new HttpEntity<>(savedUser), Void.class, savedUser.getId());

        Assertions.assertThat(userResponseEntity).isNotNull();

        Assertions.assertThat(userResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("update non-existent user should return NotFound")
    void updateNonExistentUser_return_NotFound(){

        UserEntity savedUser = userRepository.save(UserEntityFactory.createUserToBeSaved());

        savedUser.setName("João John");

        ResponseEntity<Void> userResponseEntity = testRestTemplate.exchange("/users/{id}",
                HttpMethod.PUT,new HttpEntity<>(savedUser), Void.class, -1L);

        Assertions.assertThat(userResponseEntity).isNotNull();

        Assertions.assertThat(userResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("update invalid request should return BadRequest")
    void updateInvalidRequest_return_BadRequest(){

        ResponseEntity<Void> userResponseEntity = testRestTemplate.exchange("/users/{id}",
                HttpMethod.PUT,null, Void.class, -1L);

        Assertions.assertThat(userResponseEntity).isNotNull();

        Assertions.assertThat(userResponseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("delete removes user when successful")
    void delete_RemovesUser_WhenSuccessful(){
        UserEntity savedUser = userRepository.save(UserEntityFactory.createUserToBeSaved());

        ResponseEntity<Void> userResponseEntity = testRestTemplate.exchange("/users/{id}",
                HttpMethod.DELETE,null, Void.class, savedUser.getId());

        Assertions.assertThat(userResponseEntity).isNotNull();

        Assertions.assertThat(userResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("delete non-existent user should return NotFound")
    void deleteNonExistentUser_return_NotFound(){

        ResponseEntity<Void> userResponseEntity = testRestTemplate.exchange("/users/{id}",
                HttpMethod.DELETE,null, Void.class, -1L);

        Assertions.assertThat(userResponseEntity).isNotNull();

        Assertions.assertThat(userResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
