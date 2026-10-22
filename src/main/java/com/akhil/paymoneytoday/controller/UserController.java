package com.akhil.paymoneytoday.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.akhil.paymoneytoday.dto.account.request.UserCreateDTO;
import com.akhil.paymoneytoday.dto.account.response.UserResponseDTO;
import com.akhil.paymoneytoday.service.UserService;

@RestController
public class UserController {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public UserResponseDTO createUser(@RequestBody UserCreateDTO userCreateDTO) {
        return userService.createUser(userCreateDTO);
    }

    @GetMapping("/user/{id}")
    public UserResponseDTO readUser(@PathVariable Long id) {
        return userService.readUser(id);
    }

    @PutMapping("/user/{id}")
    public UserResponseDTO updateUser(@PathVariable Long id, @RequestBody UserCreateDTO userCreateDTO) {
        return userService.updateUser(id, userCreateDTO);
    }

    @DeleteMapping("/user/{id}")
    public UserResponseDTO deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

}
