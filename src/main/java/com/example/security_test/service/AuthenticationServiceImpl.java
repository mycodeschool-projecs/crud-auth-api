package com.example.security_test.service;

import com.example.security_test.model.Role;
import com.example.security_test.model.SignUpRequest;
import com.example.security_test.model.SigninRequest;
import com.example.security_test.model.User;
import com.example.security_test.repository.UserRepository;
import com.example.security_test.system.logs.StructuredLogger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EventPublisherService eventPublisherService;
    private final AuthenticationManager authenticationManager;
    @Override
    public JwtAuthenticationResponse signup(SignUpRequest request) {
        var user = User.builder().firstName(request.getFirstName()).lastName(request.getLastName())
                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER).build();
        userRepository.save(user);

        // Publish user creation event
        try {
            log.info("Publishing user creation event for user: {}", user.getEmail());
            eventPublisherService.publishCreateEvent(
                "User", 
                user.getId().toString(), 
                String.format("User created: %s %s (%s)", user.getFirstName(), user.getLastName(), user.getEmail())
            );
        } catch (Exception e) {
            log.error("Failed to publish user creation event: {}", e.getMessage(), e);
            // Don't fail the signup process if event publishing fails
        }


        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    @Override
    public JwtAuthenticationResponse signin(SigninRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        var jwt = jwtService.generateToken(user);

        // Publish login event
        try {
            log.info("Publishing login event for user: {}", user.getEmail());
            eventPublisherService.publishLoginEvent(
                "User", 
                user.getId().toString(), 
                String.format("User logged in: %s %s (%s)", user.getFirstName(), user.getLastName(), user.getEmail())
            );
        } catch (Exception e) {
            log.error("Failed to publish login event: {}", e.getMessage(), e);
            // Don't fail the signin process if event publishing fails
        }

        return JwtAuthenticationResponse.builder().token(jwt).build();
    }
}
