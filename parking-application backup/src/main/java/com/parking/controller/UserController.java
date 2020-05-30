package com.parking.controller;

import com.parking.dto.UserDTO;
import com.parking.exceptions.NotFoundException;
import com.parking.service.UserService;

import static com.parking.controller.utils.ResponseCreator.fromList;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/users")
@Api("User controller")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    @ApiOperation("create user")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        userDTO = userService.createUser(userDTO);
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @GetMapping
    @ApiParam(name = "name", type = "String", value = "User's full name", example = "Mickey Mouse")
    @ApiOperation("Get all users with the provided name or all users if the name is not specified")
    public ResponseEntity<List<UserDTO>> getAllUsersOrUserByName(@RequestParam(required = false) String name) {
        return Strings.isBlank(name)
                ? fromList(userService.getAllUsers())
                : fromList(userService.getUserByName(name));
    }

    @GetMapping("/{id}")
    @ApiOperation("get user by id")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseThrow(() -> new NotFoundException(
                        String.format("User with id %d cannot be found", id)
                ));
    }

    @PutMapping("/{id}")
    @ApiOperation("update user")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        return userService.updateUser(id, userDTO)
                .map(updatedUser -> new ResponseEntity<>(updatedUser, HttpStatus.OK))
                .orElseThrow(() -> new NotFoundException(
                        String.format("User with id %d cannot be found", id)
                ));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("delete user")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping()
    @ApiOperation("delete all users")
    public ResponseEntity<UserDTO> deleteAllUsers() {
        userService.deleteAllUsers();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
