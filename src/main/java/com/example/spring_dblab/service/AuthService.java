package com.example.spring_dblab.service;

import com.example.spring_dblab.dto.LoginDto;
import com.example.spring_dblab.dto.RefreshTokenDto;
import com.example.spring_dblab.dto.SignupDto;
import com.example.spring_dblab.jwt.TokenInfo;
import com.example.spring_dblab.jwt.JwtTokenProvider;
import com.example.spring_dblab.model.User;
import com.example.spring_dblab.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.spring_dblab.utils.SecurityUtil.getCurrentMemberId;

@Service
public class AuthService {
    private final BCryptPasswordEncoder encoder;
    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    private final CustomUserDetailService customUserDetailService;

    @Autowired
    public AuthService(BCryptPasswordEncoder encoder, UserRepository userRepository, AuthenticationManagerBuilder authenticationManagerBuilder, JwtTokenProvider jwtTokenProvider, CustomUserDetailService customUserDetailService) {
        this.encoder = encoder;
        this.userRepository = userRepository;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.customUserDetailService = customUserDetailService;
    }

    public TokenInfo login(LoginDto loginDto) {
        try {
            String email = loginDto.getEmail();
            String password = loginDto.getPassword();

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

            TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

            return tokenInfo;
        } catch(Exception err) {
            err.printStackTrace();
            throw err;
        }
    }

    public String signUp (SignupDto signupDto) {
        try {
            String password_hash = encoder.encode(signupDto.getPassword());
            String email = signupDto.getEmail();
            List role = new ArrayList<>(Arrays.asList("USER"));
            String name = signupDto.getName();
            String nickName = signupDto.getNickName();

            User user = new User(email, password_hash, role, name, nickName);

            userRepository.save(user);

            return "success";
        } catch(Exception err) {
            err.printStackTrace();
            throw err;
        }
    }

    public Optional<User> reLogin () {
        try {
            Optional<User> user = userRepository.findByEmail(getCurrentMemberId());
            return user;
        } catch (Exception err) {
            throw err;
        }
    }

    public TokenInfo refreshToken(RefreshTokenDto refreshTokenDto) throws Exception {
        try {
            if(!jwtTokenProvider.validateToken(refreshTokenDto.getRefreshToken())) {
                throw new Exception("RefreshToken is Invalid");
            }

            Claims claims = jwtTokenProvider.parseClaims(refreshTokenDto.getRefreshToken());

            Optional<User> user = userRepository.findByEmail(claims.getSubject());
            if (!user.isPresent()) {
                throw new Exception("User not found");
            }

            Collection<? extends GrantedAuthority> authorities = user.get().getAuthorities();
            Collection<GrantedAuthority> newAuthorities = new ArrayList<>();

            for (GrantedAuthority authority : authorities) {
                if (authority.getAuthority().equals("USER")) {
                    newAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                }
                if (authority.getAuthority().equals("ADMIN")) {
                    newAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                }
                if (authority.getAuthority().equals("ORGANIZER")) {
                    newAuthorities.add(new SimpleGrantedAuthority("ROLE_ORGANIZER"));
                }
            }

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user.get().getUsername(),null,newAuthorities);

            TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

            return tokenInfo;
        } catch(Exception err) {
            throw err;
        }
    }

}
