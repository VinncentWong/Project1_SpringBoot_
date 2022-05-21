package com.project.util;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.project.entities.Customer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {
    
    @Value("${jwt.secret}")
    private String secret;
    
    public String generateToken(Customer customer){
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", customer.getEmail());
        claims.put("name", customer.getPassword());
        String token = Jwts.builder()
                            .setSubject(customer.getName())
                            .setIssuedAt(new Date())
                            .setExpiration(new Date(new Date().getTime() + 1000000000L))
                            .setClaims(claims)
                            .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(secret.getBytes()))
                            .compact();     
        return token;        
    }
}
