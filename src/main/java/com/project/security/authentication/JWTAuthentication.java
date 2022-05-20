package com.project.security.authentication;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class JWTAuthentication extends UsernamePasswordAuthenticationToken{

    public JWTAuthentication(Object principal, Object credentials) {
        super(principal, credentials);
    }
    
    public JWTAuthentication(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
