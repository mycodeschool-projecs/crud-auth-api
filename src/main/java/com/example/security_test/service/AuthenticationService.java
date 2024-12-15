package com.example.security_test.service;

import com.example.security_test.model.SignUpRequest;
import com.example.security_test.model.SigninRequest;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SigninRequest request);
}