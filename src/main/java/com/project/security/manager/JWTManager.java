package com.project.security.manager;

import com.project.security.provider.JWTProvider;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;


public class JWTManager implements AuthenticationManager{

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return new JWTProvider().authenticate(authentication);
    }
}
