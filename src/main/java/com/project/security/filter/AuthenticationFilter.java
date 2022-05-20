package com.project.security.filter;

import java.io.IOException;

import javax.el.PropertyNotFoundException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

public class AuthenticationFilter extends OncePerRequestFilter{

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException, PropertyNotFoundException {
       
        if(request.getHeader("email") == null && request.getHeader("password") == null){
            throw new PropertyNotFoundException("Property can't be empty! ");
        }
        
    }

    
    
}
