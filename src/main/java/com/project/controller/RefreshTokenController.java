package com.project.controller;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.project.entities.JwtToken;
import com.project.response.AppResponse;
import com.project.util.JWTUtil;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@RestController
public class RefreshTokenController {
    
    @GetMapping("/api/refresh")
    public AppResponse getRefreshToken(@RequestBody JwtToken token) throws Exception{
        String role = "";
        try{
            Claims exp = Jwts
                            .parser()
                            .setSigningKey(Base64.getEncoder()
                            .encodeToString(new JWTUtil().getSecretKey().getBytes()))
                            .parseClaimsJws(token.getToken())
                            .getBody();
            boolean isExpire = false;
            for(Map.Entry<String, Object> pair: exp.entrySet()){
                if(pair.getKey().equals("exp")){
                    if(new Date(System.currentTimeMillis()).getTime() < Long.parseLong(pair.getValue().toString())){
                        throw new JwtException("Token still not expired! ");
                    } else {
                        isExpire = true;
                    }
                }
                if(pair.getKey().equals("role")){
                    role = pair.getValue().toString();
                }
            }
            if(isExpire){
                exp.put("exp", new Date(System.currentTimeMillis() + 1000000000L));
            }
            String refreshToken = new JWTUtil().generateRefreshToken(role);
            if(refreshToken.equals(token.getRefreshToken())){
                String newToken = Jwts.builder()
                                    .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(new JWTUtil().getSecretKey().getBytes()))
                                    .setSubject(exp.getSubject())
                                    .setClaims(exp)
                                    .setIssuedAt(new Date())
                                    .compact();
                Map<String, Object> responseData = new HashMap<>();
                responseData.put("new token", newToken);
                AppResponse response = new AppResponse();
                response.setData(responseData);
                response.setSuccess(true);
                response.setMessage("Refresh token and expired access token is valid! ");
                return response;
            } else {
                throw new JwtException("Refresh token or Access token doesn't valid! ");
            }
            
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
}
