package com.project.security.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.security.authentication.JWTAuthentication;
import com.project.security.provider.JWTProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JWTFilter extends OncePerRequestFilter{

	@Autowired
    private JWTProvider provider;
	
	private static final Logger log = LoggerFactory.getLogger(JWTFilter.class);
	
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException{
    	if(provider == null) {
    		log.info("provider = " + provider);
    		return;
    	}
        String header = request.getHeader("Authorization");
        if(header == null || !header.startsWith("Bearer ")){
            throw new ServletException("Token doesn't valid");
        } else {
            String token = header.substring(7,header.length());
            JWTAuthentication authentication = new JWTAuthentication(token, null);
            Authentication authenticate = provider.authenticate(authentication);
            if(authenticate.isAuthenticated()) {
            	SecurityContextHolder.getContext().setAuthentication(authenticate);
            	filterChain.doFilter(request, response);
            } else {
            	throw new ServletException("token doesn't valid");
            }
        }
    }

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		log.info("Masuk ke should not filter");
		String url = request.getRequestURI() + "";
		if(url.equals("/admin/login") || url.equals("/admin/create")) {
			return true;
		} else {
			return false;
		}
	}
}
