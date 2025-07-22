package com.localdelivery.local_delivery.services;

import com.localdelivery.local_delivery.dto.LoginRequest;
import com.localdelivery.local_delivery.dto.RegisterRequest;
import com.localdelivery.local_delivery.exceptions.EtAuthException;
import com.localdelivery.local_delivery.models.User;
import com.localdelivery.local_delivery.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(RegisterRequest registerRequest) throws EtAuthException {

        if (userRepository.existsByEmail(registerRequest.getEmail().toLowerCase())) {
            throw new EtAuthException("Email already in use");
        }

        User user = new User();
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail().toLowerCase());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole("admin");
        user.setIsActive(true);

        return userRepository.save(user);
    }

    @Override
    public User loginUser(LoginRequest loginRequest) throws EtAuthException {

        User user = userRepository.findByEmail(loginRequest.getEmail().toLowerCase())
                .orElseThrow(() -> new EtAuthException("Invalid credentials"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new EtAuthException("Invalid credentials");
        }

        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        return user;
    }
}
