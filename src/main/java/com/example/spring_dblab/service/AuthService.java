package com.example.spring_dblab.service;

import com.example.spring_dblab.dto.LoginDto;
import com.example.spring_dblab.dto.RefreshTokenDto;
import com.example.spring_dblab.dto.SignupDto;
import com.example.spring_dblab.enums.RoleEnum;
import com.example.spring_dblab.jwt.TokenInfo;
import com.example.spring_dblab.jwt.JwtTokenProvider;
import com.example.spring_dblab.entitiy.User;
import com.example.spring_dblab.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.example.spring_dblab.utils.SecurityUtil.getCurrentMemberId;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final BCryptPasswordEncoder encoder;
    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailService customUserDetailService;

    public TokenInfo login(LoginDto loginDto) {
        try {
            String email = loginDto.getEmail();
            String password = loginDto.getPassword();

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

            return jwtTokenProvider.generateToken(authentication);
        } catch(Exception err) {
            err.printStackTrace();
            throw err;
        }
    }

    public String signUp (SignupDto signupDto) {
        try {
            String password_hash = encoder.encode(signupDto.getPassword());
            String email = signupDto.getEmail();
            List<RoleEnum> role = new ArrayList<>(Arrays.asList(RoleEnum.USER));
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

    public Optional<TokenInfo> reLogin () {
        try {
            Optional<User> user = userRepository.findByEmail(getCurrentMemberId());
            return Optional.empty();
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

            return jwtTokenProvider.generateToken(authentication);
        } catch(Exception err) {
            throw err;
        }
    }

}
