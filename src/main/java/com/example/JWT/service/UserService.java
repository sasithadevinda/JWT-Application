package com.example.JWT.service;

import com.example.JWT.entity.User;
import com.example.JWT.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    public Optional<User> findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }
}
