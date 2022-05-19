package com.project.security;

import java.util.Optional;

import com.project.entities.Customer;
import com.project.repository.CustomerRepository;
import com.project.security.userdetails.CustomerDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomCustomerDetailsService implements UserDetailsService{

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Customer> customer = customerRepository.findByName(username);
        if(customer.isEmpty()){
            throw new UsernameNotFoundException("Customer data doesn't found in database !");
        }
        CustomerDetails customerDetails = new CustomerDetails(customer.get());
        return customerDetails;
    }
    
}
