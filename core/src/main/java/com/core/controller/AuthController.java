package com.core.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @PostMapping("/login")
    public String login(@RequestBody UserCredentials userCredentials) {
        if ("user".equals(userCredentials.getUsername()) && "admin".equals(userCredentials.getPassword())) {
            return "Login successful";
        }
        return "Invalid credentials";
    }
}
