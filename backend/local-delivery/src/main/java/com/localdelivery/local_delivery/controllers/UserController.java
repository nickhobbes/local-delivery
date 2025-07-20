package com.localdelivery.local_delivery.controllers;

import com.localdelivery.local_delivery.dto.LoginRequest;
import com.localdelivery.local_delivery.dto.RegisterRequest;
import com.localdelivery.local_delivery.models.User;
import com.localdelivery.local_delivery.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody RegisterRequest registerRequest) {

        User user = userService.registerUser(registerRequest);

        Map<String, String> createdResponse = new HashMap<>();
        createdResponse.put("message", "user registered successfully");

        return new ResponseEntity<>(createdResponse, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody LoginRequest loginRequest) {

        User user = userService.loginUser(loginRequest);

        Map<String, String> createdResponse = new HashMap<>();
        createdResponse.put("message", "user logged in successfully");

        return new ResponseEntity<>(createdResponse, HttpStatus.OK);
    }

}
