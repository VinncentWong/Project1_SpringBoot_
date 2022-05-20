package com.project.security.provider;

import com.project.security.authentication.AuthAuthentication;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class AuthProvider implements AuthenticationProvider{

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AuthAuthentication.class.equals(authentication);
    }
    
}
