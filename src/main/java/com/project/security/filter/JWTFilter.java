package com.project.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.security.authentication.JWTAuthentication;
import com.project.security.manager.JWTManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
@Component
public class JWTFilter extends OncePerRequestFilter{

    private final Logger log = LoggerFactory.getLogger(JWTFilter.class);
    @Autowired
    private JWTManager jwtManager;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if(header == null){
            filterChain.doFilter(request, response);
        } else {
            if(!header.startsWith("Bearer ")){
                log.error("Token header doesn't valid");
            }
        }
        String token = header.substring(7,header.length());
        Authentication authenticate = jwtManager.authenticate(new JWTAuthentication(token, null));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        filterChain.doFilter(request, response);
    }
    
}
