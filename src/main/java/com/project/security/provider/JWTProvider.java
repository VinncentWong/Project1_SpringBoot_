package com.project.security.provider;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.project.security.authentication.JWTAuthentication;
import com.project.util.JWTUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public class JWTProvider implements AuthenticationProvider{
    
    private final Logger log = LoggerFactory.getLogger(JWTProvider.class);

    private String secretKey = new JWTUtil().getSecretKey();

    public JWTProvider() {}

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try{
            Jwts.parser().setSigningKey(Base64.getEncoder().encodeToString(secretKey.getBytes())).parseClaimsJws(authentication.getPrincipal().toString());
            Claims exp = Jwts.parser().setSigningKey(Base64.getEncoder().encodeToString(secretKey.getBytes())).parseClaimsJws(authentication.getPrincipal().toString()).getBody();
            log.info("Isi collection = " + exp);
            for(Map.Entry<String, Object> pair: exp.entrySet()){
                if(pair.getKey().equals("exp")){
                    if(new Date(System.currentTimeMillis()).getTime() > Long.parseLong(pair.getValue().toString())){
                        throw new JwtException("Token Expired! ");
                    }
                }
            }
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
