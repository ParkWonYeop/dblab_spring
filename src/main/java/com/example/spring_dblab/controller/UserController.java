package com.example.spring_dblab.controller;

import com.example.spring_dblab.service.UserService;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    public String getHello() {
        return userService.getHello();
    }
}
