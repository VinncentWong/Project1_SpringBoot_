package com.project.security.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.project.security.provider.JWTProvider;

public class JWTManager implements AuthenticationManager{
	
	@Autowired
	private JWTProvider provider;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		return provider.authenticate(authentication);
	}
}
