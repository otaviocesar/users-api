package com.mlb.usersapi.adapters.inbound;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.mlb.usersapi.adapters.inbound.dtos.UserDTO;
import com.mlb.usersapi.adapters.inbound.mappers.UserDTOToUserMapper;
import com.mlb.usersapi.application.core.domain.User;
import com.mlb.usersapi.application.ports.primary.SaveUserServicePort;
import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final SaveUserServicePort saveUserServicePort;

    private final UserDTOToUserMapper userDTOToUserMapper;

    @PostMapping
    public ResponseEntity<User> save(@RequestBody @Valid UserDTO saveUserDTO) {
        User user = saveUserServicePort.save(userDTOToUserMapper.mapper(saveUserDTO));
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
