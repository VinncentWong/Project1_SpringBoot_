package com.project.security.provider;

import java.util.Base64;
import java.util.Date;
import java.util.Map;

import com.project.security.authentication.JWTAuthentication;
import com.project.security.userdetailsservice.JWTDetailsService;
import com.project.util.JWTUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public class JWTProvider implements AuthenticationProvider{

    private String secretKey = new JWTUtil().getSecretKey();

    @Autowired
    private JWTDetailsService jwtDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try{
            Claims exp = Jwts
                            .parser()
                            .setSigningKey(Base64.getEncoder()
                            .encodeToString(secretKey.getBytes()))
                            .parseClaimsJws(authentication.getPrincipal().toString())
                            .getBody();
            for(Map.Entry<String, Object> pair: exp.entrySet()){
                if(pair.getKey().equals("exp")){
                    if(new Date(System.currentTimeMillis()).getTime() > Long.parseLong(pair.getValue().toString())){
                        throw new JwtException("Token Expired! ");
                    }
                }
            }
            String subject = exp.getSubject();
            UserDetails details = jwtDetailsService.loadUserByUsername(subject);
            return new JWTAuthentication(details.getUsername(), details.getPassword(), details.getAuthorities());
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
