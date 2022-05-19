package com.project.security.userdetailsservice;

import java.util.Optional;

import com.project.entities.Customer;
import com.project.repository.CustomerRepository;
import com.project.security.CustomerDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JWTDetailsService implements UserDetailsService{

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Customer> customer = customerRepository.findByName(username);
        UserDetails customerDetails = new CustomerDetails(customer.get());
        return customerDetails;
    }
    
}
