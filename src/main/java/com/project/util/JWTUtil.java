package com.project.util;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.project.entities.Admin;
import com.project.entities.Customer;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {
    
    private final String SECRET_KEY = "ASDKDLAMSDKLMASKMFGSDKJQ1294052381239RIEIO12390LKANLASD18023";
    
    public String generateToken(Customer customer){
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", customer.getEmail());
        claims.put("name", customer.getName());
        claims.put("password", customer.getPassword());
        claims.put("randomNumber", (int)(Math.random() * 1001));
        String token = Jwts.builder()
                            .setSubject(customer.getName())
                            .setIssuedAt(new Date())
                            .setExpiration(new Date(new Date().getTime() + 1000000000L))
                            .setClaims(claims)
                            .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(SECRET_KEY.getBytes()))
                            .compact();     
        return token;        
    }

    public String generateToken(Admin admin){
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", admin.getEmail());
        claims.put("name", admin.getName());
        claims.put("password", admin.getPassword());
        claims.put("randomNumber", (int)(Math.random() * 1001));
        String token = Jwts.builder()
                            .setSubject(admin.getName())
                            .setIssuedAt(new Date())
                            .setExpiration(new Date(new Date().getTime() + 1000000000L))
                            .setClaims(claims)
                            .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(SECRET_KEY.getBytes()))
                            .compact();     
        return token;        
    }

    public String getSubject(String token){
        return  Jwts
                .parser()
                .setSigningKey(Base64.getEncoder().encodeToString(this.SECRET_KEY.getBytes()))
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String getSecretKey(){
        return this.SECRET_KEY;
    }
}
