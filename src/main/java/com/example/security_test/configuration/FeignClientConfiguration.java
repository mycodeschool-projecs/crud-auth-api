package com.example.security_test.configuration;

import com.example.security_test.service.JwtService;
import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

@Configuration
@RequiredArgsConstructor
public class FeignClientConfiguration {

    private final JwtService jwtService;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            // Create a service account user for the Feign client
            UserDetails serviceAccount = new User(
                "service-account@example.com",
                "not-used-password",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_SERVICE"))
            );

            // Generate a JWT token for the service account
            String token = jwtService.generateToken(serviceAccount);

            // Add Authorization header to all Feign requests
            requestTemplate.header(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        };
    }
}
