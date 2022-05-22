package com.project.util;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.project.entities.Admin;
import com.project.entities.Customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {

    private final Logger log = LoggerFactory.getLogger(JWTUtil.class);
    
    private final String SECRET_KEY = "ASDKDLAMSDKLMASKMFGSDKJQ1294052381239RIEIO12390LKANLASD18023";
    
    private final String SECRET_REFRESH_TOKEN_CUSTOMER = "REFRESH_TOKEN_X123_..1212120099384//[]{74C";

    private final String SECRET_REFRESH_TOKEN_ADMIN = "REFRESH_TOKEN_01291231_//{21AA}1A9XX2LK[P]V11A";
    
    public String generateToken(Customer customer){
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", customer.getEmail());
        claims.put("name", customer.getName());
        claims.put("password", customer.getPassword());
        claims.put("randomNumber", (int)(Math.random() * 1001));
        claims.put("exp", new Date(System.currentTimeMillis() + (300000)));
        claims.put("role", "ROLE_CUSTOMER");
        String token = Jwts.builder()
                            .setSubject(customer.getName())
                            .setIssuedAt(new Date())
                            .setClaims(claims)
                            .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(SECRET_KEY.getBytes()))
                            .compact();   
        log.info("Expire = " + new Date(System.currentTimeMillis() + (300000)));
        return token;        
    }

    public String generateToken(Admin admin){
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", admin.getEmail());
        claims.put("name", admin.getName());
        claims.put("password", admin.getPassword());
        claims.put("randomNumber", (int)(Math.random() * 1001));
        claims.put("exp", new Date(System.currentTimeMillis() + (300000)));
        claims.put("role", "ROLE_ADMIN");
        String token = Jwts.builder()
                            .setSubject(admin.getName())
                            .setIssuedAt(new Date())
                            .setExpiration(new Date(new Date().getTime() + 1000000000L))
                            .setClaims(claims)
                            .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(SECRET_KEY.getBytes()))
                            .compact();     
        return token;        
    }

    public String generateRefreshToken(String role) throws Exception{
        if(role.equals("ROLE_ADMIN")){
            return this.SECRET_REFRESH_TOKEN_ADMIN;
        } else if(role.equals("ROLE_CUSTOMER")){
            return this.SECRET_REFRESH_TOKEN_CUSTOMER;
        } else {
            throw new Exception("Role doesn't valid! ");
        }
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

    public String getSECRET_REFRESH_TOKEN_CUSTOMER() {
        return SECRET_REFRESH_TOKEN_CUSTOMER;
    }

    public String getSECRET_REFRESH_TOKEN_ADMIN() {
        return SECRET_REFRESH_TOKEN_ADMIN;
    }
}
