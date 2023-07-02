package com.example.spring_dblab.service;

import com.example.spring_dblab.dto.AuthDto;
import com.example.spring_dblab.jwt.TokenInfo;
import com.example.spring_dblab.jwt.JwtTokenProvider;
import com.example.spring_dblab.model.User;
import com.example.spring_dblab.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthService {
    private final BCryptPasswordEncoder encoder;
    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    @Autowired
    public AuthService(BCryptPasswordEncoder encoder, UserRepository userRepository, AuthenticationManagerBuilder authenticationManagerBuilder, JwtTokenProvider jwtTokenProvider) {
        this.encoder = encoder;
        this.userRepository = userRepository;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public TokenInfo login(AuthDto authDto) {
        try {
            String email = authDto.getEmail();
            String password = authDto.getPassword();

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

            TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

            return tokenInfo;
        } catch(Exception err) {
            err.printStackTrace();
            throw err;
        }
    }

    public String signUp (AuthDto authDto) {
        try {
            String password_hash = encoder.encode(authDto.getPassword());
            String email = authDto.getEmail();
            List role = new ArrayList<>();

            User user = new User(email, password_hash, role);

            userRepository.save(user);

            return "success";
        } catch(Exception err) {
            err.printStackTrace();
            throw err;
        }
    }
}
