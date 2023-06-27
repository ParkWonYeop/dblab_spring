package com.example.spring_dblab.service;

import com.example.spring_dblab.dto.UserDTO;
import com.example.spring_dblab.model.User;
import com.example.spring_dblab.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUser(String email) {
        Optional<User> userData = this.userRepository.findByEmail(email);
        return userData;
    }

    public Boolean setUser(UserDTO userDTO) {
        try {
            User user = new User();
            return true;
        } catch(Exception err) {
            err.printStackTrace();
            throw err;
        }
    }
}
