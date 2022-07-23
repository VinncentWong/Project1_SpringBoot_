package com.project.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class Config {
    
    @Bean
    public BCryptPasswordEncoder bcrypt(){
        return new BCryptPasswordEncoder();
    }
}
