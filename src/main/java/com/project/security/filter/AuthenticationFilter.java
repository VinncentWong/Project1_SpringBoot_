package com.project.security.filter;

import java.io.IOException;
import java.util.ArrayList;

import javax.el.PropertyNotFoundException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.security.authentication.AuthAuthentication;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class AuthenticationFilter extends OncePerRequestFilter{

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException, PropertyNotFoundException {
        SecurityContextHolder.getContext().setAuthentication(new AuthAuthentication(request.getHeader("email"), request.getHeader("password"), new ArrayList<SimpleGrantedAuthority>()));
        filterChain.doFilter(request, response);
    }
    
}
