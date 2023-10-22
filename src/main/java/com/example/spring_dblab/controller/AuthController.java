package com.example.spring_dblab.controller;

import com.example.spring_dblab.dto.LoginDto;
import com.example.spring_dblab.dto.RefreshTokenDto;
import com.example.spring_dblab.dto.SignupDto;
import com.example.spring_dblab.jwt.TokenInfo;
import com.example.spring_dblab.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
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

    //"/auth/token?grant_type=refresh"
    @PostMapping("/login")
    public TokenInfo login(@RequestBody LoginDto loginDto) {
        return authService.login(loginDto);
    }

    @PostMapping("/signup")
    public String signUp(@RequestBody SignupDto signupDto) { return  authService.signUp(signupDto);}

    @GetMapping("/token")
    public Optional<TokenInfo> Token(@ModelAttribute("dto") Object dto) throws Exception {
        if(dto instanceof RefreshTokenDto) {
            RefreshTokenDto refreshTokenDto = (RefreshTokenDto) dto;
            return Optional.ofNullable(authService.refreshToken(refreshTokenDto));
        } else if(dto == null) {
            return authService.reLogin();
        }
        return Optional.empty();
    }

    /*@ModelAttribute
    public void setupDto(@RequestParam("grant_type") String grantType, Model model) {
        if ("refresh".equals(grantType)) {
            model.addAttribute("dto", new RefreshTokenDto());
        } else if("recertification".equals(grantType)) {
            model.addAttribute("dto", null);
        }
    }*/
}
