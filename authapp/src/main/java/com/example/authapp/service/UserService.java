package com.example.authapp.service;

import com.example.authapp.dto.SignupDto;
import com.example.authapp.model.User;
import com.example.authapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(SignupDto signupDto) {
        // Kiểm tra email đã tồn tại chưa
        if (userRepository.existsByEmail(signupDto.getEmail())) {
            throw new RuntimeException("Email is already taken!");
        }

        // Tạo user mới
        User user = new User(
                signupDto.getEmail(),
                passwordEncoder.encode(signupDto.getPassword()),
                signupDto.getFullName()
        );

        // Mặc định mỗi user đều có ROLE_USER
        user.setRoles(new HashSet<>(Collections.singletonList("USER")));

        // Lưu vào database
        return userRepository.save(user);
    }
}