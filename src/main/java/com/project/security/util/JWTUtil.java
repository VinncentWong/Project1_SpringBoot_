package com.project.security.util;

import java.util.Base64;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.project.service.CustomCustomerDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {
    
    @Value("${jwt.key}")
    private String secretKey;

    @Autowired
    private CustomCustomerDetailsService customerDetails;

    public String createToken(String username, SimpleGrantedAuthority roles){
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("authorities", roles);

        Date now = new Date();
        Date exp = new Date(now.getTime() + 100000);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(secretKey.getBytes()))
                .compact();
    }

    public Authentication getAuthentication(String token){
        String customerClaims = Jwts.parser().setSigningKey(Base64.getEncoder().encodeToString(secretKey.getBytes())).parseClaimsJwt(token).getBody().getSubject();
        UserDetails customer = customerDetails.loadUserByUsername(customerClaims);
        return new UsernamePasswordAuthenticationToken(customer, "", customer.getAuthorities());
    }

    public String resolveToken(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        if(header != null && header.startsWith("Bearer ")){
            return header.substring(7, header.length());
        } 
        return null;
    }

    public boolean validateToken(String token) throws Exception{
        try{
            Jwts.parser().setSigningKey(Base64.getEncoder().encodeToString(secretKey.getBytes())).parseClaimsJws(token);
            return true;
        }
        catch(JwtException | IllegalArgumentException ex){
            throw new Exception("Token doesn't valid or expired !");
        }
    }
}
