package com.localdelivery.local_delivery.controllers;

import com.localdelivery.local_delivery.Constants;
import com.localdelivery.local_delivery.dto.LoginRequest;
import com.localdelivery.local_delivery.dto.RegisterRequest;
import com.localdelivery.local_delivery.models.User;
import com.localdelivery.local_delivery.services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.util.Date;
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

        return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody LoginRequest loginRequest) {

        User user = userService.loginUser(loginRequest);

        return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
    }

    public Map<String, String> generateJWTToken(User user) {

        long timestamp = System.currentTimeMillis();
        Date issuedAt = new Date(timestamp);
        Date expiration = new Date(timestamp + Constants.ACCESS_TOKEN_EXPIRATION_MS);

        SecretKey key = Keys.hmacShaKeyFor(Constants.API_SECRET_KEY.getBytes());

        String token = Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuer("local-delivery-app")
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .claim("name", user.getName())
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return response;
    }
}
