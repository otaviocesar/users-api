package com.mlb.usersapi.adapters.inbound;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.mlb.usersapi.adapters.inbound.dtos.PatchUserDTO;
import com.mlb.usersapi.adapters.inbound.dtos.UserDTO;
import com.mlb.usersapi.adapters.inbound.mappers.PatchUserDTOToUserMapper;
import com.mlb.usersapi.adapters.inbound.mappers.UserDTOToUserMapper;
import com.mlb.usersapi.application.core.domain.User;
import com.mlb.usersapi.application.core.exception.ErrorResponse;
import com.mlb.usersapi.application.ports.primary.DeleteUserServicePort;
import com.mlb.usersapi.application.ports.primary.FindAllUsersServicePort;
import com.mlb.usersapi.application.ports.primary.FindByIdUserServicePort;
import com.mlb.usersapi.application.ports.primary.SaveUserServicePort;
import com.mlb.usersapi.application.ports.primary.UpdateUserServicePort;

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

    private final FindByIdUserServicePort findByIdUserServicePort;

    private final DeleteUserServicePort deleteUserServicePort;

    private final UpdateUserServicePort updateUserServicePort;

    private final UserDTOToUserMapper userDTOToUserMapper;

    private final PatchUserDTOToUserMapper patchUserDTOToUserMapper;
    

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
    public ResponseEntity<User> save(@RequestBody @Valid UserDTO userDTO) {
        User user = saveUserServicePort.save(userDTOToUserMapper.mapper(userDTO));
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
    public ResponseEntity<List<User>> getAll(@RequestParam(value = "name", required = false) String name){
        return ResponseEntity.status(HttpStatus.OK).body(findAllUsersServicePort.findAll(name));
    }

    @Operation(
        summary = "Find user by id",
        description = "Returns a user by id if it exists",
        tags = {"users"},
        responses = {
            @ApiResponse(responseCode = "200", description = "Ok."),
            @ApiResponse(responseCode = "400", description = "Bad Request.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not Found.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable(value = "id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(findByIdUserServicePort.findById(id));
    }

    @Operation(
        summary = "Delete user by id",
        description = "Delete a user by id if it exists",
        tags = {"users"},
        responses = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully."),
            @ApiResponse(responseCode = "400", description = "Bad Request.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not Found.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") Long id){
        deleteUserServicePort.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully.");
    }

    @Operation(
        summary = "Update a user",
        description = "This operation updates a user record if it exists",
        tags = {"users"},
        responses = {
            @ApiResponse(responseCode = "200", description = "Ok."),
            @ApiResponse(responseCode = "400", description = "Bad Request.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        }
    )
    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable(value = "id") Long id, @RequestBody @Valid UserDTO userDTO) {
        User user = userDTOToUserMapper.mapper(userDTO);
        User userUpdated = updateUserServicePort.update(id, user);
        return new ResponseEntity<>(userUpdated, HttpStatus.OK);
    }

    @Operation(
        summary = "Partially update a user",
        description = "This operation partially updates a user record if it exists",
        tags = {"users"},
        responses = {
            @ApiResponse(responseCode = "200", description = "Ok."),
            @ApiResponse(responseCode = "400", description = "Bad Request.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        }
    )
    @PatchMapping("/{id}")
    public ResponseEntity<User> patch(@PathVariable(value = "id") Long id, @RequestBody @Valid PatchUserDTO patchUserDTO) {
        User user = patchUserDTOToUserMapper.mapper(patchUserDTO);
        User userUpdated = updateUserServicePort.update(id, user);
        return new ResponseEntity<>(userUpdated, HttpStatus.OK);
    }
}
