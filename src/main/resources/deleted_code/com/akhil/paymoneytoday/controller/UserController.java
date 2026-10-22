package com.akhil.paymoneytoday.controller;

import org.springframework.web.bind.annotation.RestController;

import com.akhil.paymoneytoday.dto.account.UserCreateDTO;
import com.akhil.paymoneytoday.dto.account.UserResponseDTO;
import com.akhil.paymoneytoday.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/user")
public class UserController {
    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public UserResponseDTO createUser(@RequestBody UserCreateDTO req) {
        return userService.createUser(req);
    }

    @GetMapping("/getuserbyid/{id}")
    public UserResponseDTO getUserByIdInControllerMethod(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/getuserbyname/{name}")
    public UserResponseDTO getUserByNameInControllerMethod(@PathVariable String name) {
        return userService.getUserByName(name);
    }

    @GetMapping("/getuserbyemail/{email}")
    public UserResponseDTO getUserByEmailInControllerMethod(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }
}
