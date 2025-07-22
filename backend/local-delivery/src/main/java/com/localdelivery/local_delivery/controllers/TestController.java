package com.localdelivery.local_delivery.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("")
    public String getTests(Authentication authentication) {

        String email = authentication.getName();

        return "Authenticated! Email: " + email;
    }
}
