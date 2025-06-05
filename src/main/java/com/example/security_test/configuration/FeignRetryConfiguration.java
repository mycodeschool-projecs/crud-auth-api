package com.example.security_test.configuration;

import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignRetryConfiguration {

    @Bean
    public Retryer retryer() {
        // Retry up to 3 times with a 100ms initial backoff and 1s max backoff
        return new Retryer.Default(100, 1000, 3);
    }
}