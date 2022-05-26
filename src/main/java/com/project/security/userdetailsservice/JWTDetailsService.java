package com.project.security.userdetailsservice;

import java.util.Optional;

import com.project.entities.Admin;
import com.project.entities.Customer;
import com.project.repository.AdminRepository;
import com.project.repository.CustomerRepository;
import com.project.security.AdminDetails;
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

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Customer> customer = customerRepository.findByName(username);
        Optional<Admin> admin = adminRepository.findByName(username);
        if(customer.isEmpty()){
            return new AdminDetails(admin.get());
        } else {
            return new CustomerDetails(customer.get());
        }
    }
    
}
