package com.example.spring_dblab.controller;

import com.example.spring_dblab.dto.AuthDto;
import com.example.spring_dblab.jwt.TokenInfo;
import com.example.spring_dblab.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    AuthService authService;

    AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/login")
    public TokenInfo login(@RequestBody AuthDto authDto) {
        return authService.login(authDto);
    }

    @PostMapping("/signup")
    public String signUp(@RequestBody AuthDto authDto) { return  authService.signUp(authDto);}
}
