package com.project.security.authentication;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class AuthAuthentication extends UsernamePasswordAuthenticationToken{

    public AuthAuthentication(){
        super(null, null);
    }
    
    public AuthAuthentication(Object principal, Object credentials) {
        super(principal, credentials);
        //TODO Auto-generated constructor stub
    }
    
    public AuthAuthentication(Object principal, Object credentials,
    Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
