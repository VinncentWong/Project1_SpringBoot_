package com.project.security.manager;

import com.project.security.provider.JWTProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class JWTManager implements AuthenticationManager{

    @Autowired
    private JWTProvider jwtProvider;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return jwtProvider.authenticate(authentication);
    }
}
