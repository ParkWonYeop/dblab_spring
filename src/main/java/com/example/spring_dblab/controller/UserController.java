package com.example.spring_dblab.controller;

import com.example.spring_dblab.dto.UserDTO;
import com.example.spring_dblab.model.User;
import com.example.spring_dblab.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public Optional<User> getUser(@RequestParam(value = "email") String email) {
        return userService.getUser(email);
    }

    @PostMapping()
    public void setUser(@RequestBody UserDTO userDTO) {

    }
}
