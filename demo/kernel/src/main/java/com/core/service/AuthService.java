package com.core.service;

import org.springframework.stereotype.Service;
import com.core.controller.dto.UserCredentials;

@Service
public class AuthService {

    public boolean login(UserCredentials userCredentials) {
        // Check credentials in the service layer
        return "user".equals(userCredentials.getUsername()) && "admin".equals(userCredentials.getPassword());
    }
}

