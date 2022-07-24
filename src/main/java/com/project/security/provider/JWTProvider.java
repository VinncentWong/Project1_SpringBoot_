package com.project.security.provider;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.project.security.authentication.JWTAuthentication;
import com.project.util.JWTUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

@Component
public class JWTProvider implements AuthenticationProvider{
	
	private String secretKey = new JWTUtil().getSecretKey();

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String token = authentication.getPrincipal().toString();
		try{
            String role = "";
            String password = "";
            Claims exp = Jwts
                            .parser()
                            .setSigningKey(Base64.getEncoder()
                            .encodeToString(secretKey.getBytes()))
                            .parseClaimsJws(token)
                            .getBody();
            for(Map.Entry<String, Object> pair: exp.entrySet()){
                if(pair.getKey().equals("exp")){
                    if(new Date(System.currentTimeMillis()).getTime() > Long.parseLong(pair.getValue().toString())){
                        throw new JwtException("Token Expired! ");
                    }
                }
                if(pair.getKey().equals("role")){
                    role = role + pair.getValue();
                }
                if(pair.getKey().equals("password")){
                    password = password + pair.getValue();
                }
            }
            String principal = exp.getSubject();
            List<GrantedAuthority> list = new ArrayList<>();
            list.add(new SimpleGrantedAuthority("verified"));
            return new JWTAuthentication(principal, null, list);
		}
		catch(Exception ex) {
			return new JWTAuthentication(null, null);
		}
	}
	@Override
	public boolean supports(Class<?> authentication) {
		if(authentication.getClass().equals(JWTAuthentication.class)) {
			return true;
		}
		return false;
	}
	
	
}
