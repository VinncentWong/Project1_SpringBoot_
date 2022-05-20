package com.project.security.provider;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.project.security.authentication.JWTAuthentication;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
public class JWTProvider implements AuthenticationProvider{

    @Value("${jwt.secret}")
    private String secretKey;
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try{
            Jwts.parser().setSigningKey(Base64.getEncoder().encodeToString(secretKey.getBytes())).parseClaimsJws(authentication.getPrincipal().toString());
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("true"));
            return new JWTAuthentication(authentication.getPrincipal().toString(), null, authorities);
        }
        catch(JwtException ex){
            throw new JwtException("Token doesn't valid! ");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JWTAuthentication.class.equals(authentication);
    }
    
}
