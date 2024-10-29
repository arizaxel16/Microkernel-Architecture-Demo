package com.core.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.core.controller.dto.UserCredentials;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserCredentials userCredentials) {
        // Log the received credentials for debugging
        System.out.println("Received credentials: " + userCredentials.getUsername() + ", " + userCredentials.getPassword());

        // Check credentials
        if ("user".equals(userCredentials.getUsername()) && "admin".equals(userCredentials.getPassword())) {
            // Return 200 OK for successful login
            return ResponseEntity.ok("Login successful");
        } else {
            // Return 401 Unauthorized for invalid credentials
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}
