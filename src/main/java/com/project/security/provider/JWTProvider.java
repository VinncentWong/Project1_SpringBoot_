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

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
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
        catch(SignatureException ex){
            throw new SignatureException("Token signature doesn't valid! ");
        }
        catch(MalformedJwtException ex){
            throw new MalformedJwtException("Token doesn't valid");
        }
        catch(ExpiredJwtException ex){
            throw new JwtException("Jwt token expired");
        }
        catch(UnsupportedJwtException ex){
            throw new UnsupportedJwtException("Jwt token doesn't supported");
        }
        catch(IllegalArgumentException ex){
            throw new IllegalArgumentException("Jwt claims is empty");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JWTAuthentication.class.equals(authentication);
    }
    
}
