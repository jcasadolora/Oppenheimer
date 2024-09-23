package com.nisum.oppenheimer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

@Configuration
public class PasswordConfig {

    @Value("${password.saltLength}")
    private int saltLength;

    @Value("${password.hashLength}")
    private int hashLength;

    @Value("${password.parallelism}")
    private int parallelism;

    @Value("${password.memory}")
    private int memory;

    @Value("${password.iterations}")
    private int iterations;

    @Bean
    public Argon2PasswordEncoder argon2PasswordEncoder() {
        return new Argon2PasswordEncoder(saltLength, hashLength, parallelism, memory, iterations);
    }
}
