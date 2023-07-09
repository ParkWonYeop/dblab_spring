package com.example.spring_dblab.controller;

import com.example.spring_dblab.dto.LoginDto;
import com.example.spring_dblab.dto.RefreshTokenDto;
import com.example.spring_dblab.dto.SignupDto;
import com.example.spring_dblab.jwt.TokenInfo;
import com.example.spring_dblab.model.User;
import com.example.spring_dblab.service.AuthService;
import com.example.spring_dblab.utils.SecurityUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
    AuthService authService;

    AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/login")
    public TokenInfo login(@RequestBody LoginDto loginDto, HttpServletResponse response) {
        return authService.login(loginDto);
    }

    @PostMapping("/signup")
    public String signUp(@RequestBody SignupDto signupDto) { return  authService.signUp(signupDto);}

    @GetMapping("/relogin")
    public Optional<User> reLogin() {
        return authService.reLogin();
    }

    @PostMapping("/refresh")
    public TokenInfo refreshToken(@RequestBody RefreshTokenDto refreshTokenDto) throws Exception {
        return authService.refreshToken(refreshTokenDto);
    }
}
