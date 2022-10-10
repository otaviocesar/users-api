package com.mlb.usersapi.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.orm.jpa.JpaSystemException;

import com.mlb.usersapi.adapters.inbound.entity.UserEntity;
import com.mlb.usersapi.adapters.outbound.repository.UserRepository;
import com.mlb.usersapi.util.UserFactory;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayName("Tests for UserRepository")
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Save persists user when Successful")
    void save_PersistUserEntity_WhenSuccessful(){
        UserEntity userToBeSaved = UserFactory.createUserToBeSaved();

        UserEntity userSaved = this.userRepository.save(userToBeSaved);

        Assertions.assertThat(userSaved).isNotNull();

        Assertions.assertThat(userSaved.getId()).isNotNull();

        Assertions.assertThat(userSaved.getName()).isEqualTo(userToBeSaved.getName());
    }

    @Test
    @DisplayName("Save updates user when Successful")
    void save_UpdatesUserEntity_WhenSuccessful(){
        UserEntity userToBeSaved = UserFactory.createUserToBeSaved();

        UserEntity userSaved = this.userRepository.save(userToBeSaved);

        userSaved.setName("João John");

        UserEntity userUpdated = this.userRepository.save(userSaved);

        Assertions.assertThat(userUpdated).isNotNull();

        Assertions.assertThat(userUpdated.getId()).isNotNull();

        Assertions.assertThat(userUpdated.getName()).isEqualTo(userSaved.getName());
    }

    @Test
    @DisplayName("Delete removes user when Successful")
    void delete_RemovesUserEntity_WhenSuccessful(){
        UserEntity userToBeSaved = UserFactory.createUserToBeSaved();

        UserEntity userSaved = this.userRepository.save(userToBeSaved);

        this.userRepository.delete(userSaved);

        Optional<UserEntity> userOptional = this.userRepository.findById(userSaved.getId());

        Assertions.assertThat(userOptional).isEmpty();

    }

    @Test
    @DisplayName("Find By Name returns list of user when Successful")
    void findByName_ReturnsListOfUserEntity_WhenSuccessful(){
        UserEntity userToBeSaved = UserFactory.createUserToBeSaved();

        UserEntity userSaved = this.userRepository.save(userToBeSaved);

        String name = userSaved.getName();

        List<UserEntity> users = this.userRepository.findByName(name);

        Assertions.assertThat(users)
                .isNotEmpty()
                .contains(userSaved);
    }

    @Test
    @DisplayName("Find By Name returns empty list when no user is found")
    void findByName_ReturnsEmptyList_WhenUserEntityIsNotFound(){
        List<UserEntity> users = this.userRepository.findByName("non-existent-name");

        Assertions.assertThat(users).isEmpty();
    }

    @Test
    @DisplayName("Save throw JpaSystemException when name is empty")
    void save_ThrowsConstraintViolationException_WhenNameIsEmpty(){
		UserEntity user = UserFactory.createInvalidUser();
        Assertions.assertThatThrownBy(() -> this.userRepository.save(user))
                .isInstanceOf(JpaSystemException.class);
    }   
}