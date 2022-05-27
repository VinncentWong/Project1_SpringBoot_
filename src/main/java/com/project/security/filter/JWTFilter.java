package com.project.security.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.security.authentication.JWTAuthentication;
import com.project.util.JWTUtil;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
public class JWTFilter extends OncePerRequestFilter{

    private String secretKey = new JWTUtil().getSecretKey();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException{
        String header = request.getHeader("Authorization");
        if(header == null || !header.startsWith("Bearer ")){
            throw new ServletException("Token doesn't valid");
        } else {
            String token = header.substring(7,header.length());
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
                String subject = exp.getSubject();
                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority(role));
                Authentication authentication = new JWTAuthentication(subject, password, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
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
        filterChain.doFilter(request, response);
    }
}
