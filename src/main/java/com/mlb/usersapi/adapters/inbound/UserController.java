package com.mlb.usersapi.adapters.inbound;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.mlb.usersapi.adapters.inbound.dtos.UserDTO;
import com.mlb.usersapi.adapters.inbound.mappers.UserDTOToUserMapper;
import com.mlb.usersapi.application.core.domain.User;
import com.mlb.usersapi.application.core.exception.ErrorResponse;
import com.mlb.usersapi.application.ports.primary.FindAllUsersServicePort;
import com.mlb.usersapi.application.ports.primary.SaveUserServicePort;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "Endpoint group to create, list, update and delete users")
@AllArgsConstructor
public class UserController {

    private final SaveUserServicePort saveUserServicePort;

    private final FindAllUsersServicePort findAllUsersServicePort;

    private final UserDTOToUserMapper userDTOToUserMapper;
    

    @Operation(
        summary = "Add a user",
        description = "This operation saves a new record with the user information",
        tags = {"users"},
        responses = {
            @ApiResponse(responseCode = "201", description = "Created."),
            @ApiResponse(responseCode = "400", description = "Bad Request.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Conflit.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        }
    )
    @PostMapping(
		consumes = MediaType.APPLICATION_JSON_VALUE
	)
    public ResponseEntity<User> save(@RequestBody @Valid UserDTO saveUserDTO) {
        User user = saveUserServicePort.save(userDTOToUserMapper.mapper(saveUserDTO));
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @Operation(
        summary = "List users",
        description = "Returns list of registered users",
        tags = {"users"},
        responses = {
            @ApiResponse(responseCode = "200", description = "Ok."),
            @ApiResponse(responseCode = "400", description = "Bad Request.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not Found.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        }
    )
    @GetMapping
    public ResponseEntity<List<User>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(findAllUsersServicePort.findAll());
    }
}
