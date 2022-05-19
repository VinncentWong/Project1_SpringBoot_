package com.project.security.authmanager;

import java.util.Optional;

import com.project.entities.Customer;
import com.project.repository.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
@Component
public class CustomAuthManager implements AuthenticationManager{

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Optional<Customer> customer = customerRepository.findByName(authentication.getPrincipal().toString());
        if(customer.isPresent()){
            if(new BCryptPasswordEncoder().matches(authentication.getCredentials().toString(), customer.get().getPassword())){
                return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials());
            } else {
                throw new BadCredentialsException("Credential doesn't valid! ");
            }
        } else {
            throw new BadCredentialsException("Credential doesn't valid! ");
        }
    }
    
}
