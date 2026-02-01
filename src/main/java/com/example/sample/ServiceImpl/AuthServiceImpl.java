package com.example.sample.ServiceImpl;

import com.example.sample.DTO.Request.RegisterRequest;
import com.example.sample.Entity.User;
import com.example.sample.Repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuthServiceImpl {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(RegisterRequest registerRequest) {
        // Check if user already exists
        if (userRepo.existsByEmail(registerRequest.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }
        if (userRepo.existsByContactNo(registerRequest.getContactNo())) {
            throw new IllegalArgumentException("Contact number already registered");
        }

        // Create new user
        User user = new User();
        user.setName(registerRequest.getFirstName() + " " + registerRequest.getLastName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setContactNo(registerRequest.getContactNo());
        user.setRoles(List.of("USER"));
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);

        User savedUser = userRepo.save(user);
        log.info("User registered successfully: {}", savedUser.getEmail());
        return savedUser;
    }
}
