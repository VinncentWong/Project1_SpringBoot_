package com.project.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.security.authentication.JWTAuthentication;
import com.project.security.manager.JWTManager;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JWTFilter extends OncePerRequestFilter{
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException{
        String header = request.getHeader("Authorization");
        if(header == null || !header.startsWith("Bearer ")){
            throw new ServletException("Token doesn't valid");
        } else {
            String token = header.substring(7,header.length());
            Authentication authenticate = new JWTManager().authenticate(new JWTAuthentication(token, null));
            SecurityContextHolder.getContext().setAuthentication(authenticate);
        }
        filterChain.doFilter(request, response);
    }
    
}
