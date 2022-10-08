package com.mlb.usersapi.adapters.inbound;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mlb.usersapi.adapters.inbound.dtos.UserDTO;
import com.mlb.usersapi.adapters.inbound.mappers.UserDTOToUserMapper;
import com.mlb.usersapi.application.core.domain.User;
import com.mlb.usersapi.application.ports.primary.SaveUserServicePort;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final SaveUserServicePort saveUserServicePort;

    private final UserDTOToUserMapper userDTOToUserMapper;

    @PostMapping
    public User save(@RequestBody UserDTO saveUserDTO){
        var user = userDTOToUserMapper.mapper(saveUserDTO);
        return saveUserServicePort.save(user);
    }
}
