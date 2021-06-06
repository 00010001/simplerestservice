package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{login}")
    public User getUser(@PathVariable String login) {
        if (login == null)
            throw new IllegalArgumentException("Login name cannot be null");
        if (login.length() == 0)
            throw new IllegalArgumentException("Login name cannot be empty");
        return userService.getUser(login.trim().toLowerCase(Locale.ROOT));
    }

}
