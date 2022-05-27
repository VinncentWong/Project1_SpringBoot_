package com.project.security.userdetailsservice;

import java.util.Optional;

import com.project.entities.Admin;
import com.project.entities.Customer;
import com.project.repository.AdminRepository;
import com.project.repository.CustomerRepository;
import com.project.security.AdminDetails;
import com.project.security.CustomerDetails;
import com.project.util.ApplicationContextUtil;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JWTDetailsService implements UserDetailsService{

    private CustomerRepository customerRepository;

    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(customerRepository == null || adminRepository == null){
            customerRepository = new ApplicationContextUtil().getContext().getBean(CustomerRepository.class);
            adminRepository = new ApplicationContextUtil().getContext().getBean(AdminRepository.class);
        }
        Optional<Customer> customer = customerRepository.findByName(username);
        Optional<Admin> admin = adminRepository.findByName(username);
        if(customer.isEmpty()){
            return new AdminDetails(admin.get());
        } else if(admin.isEmpty()){
            return new CustomerDetails(customer.get());
        } else {
            throw new UsernameNotFoundException("User doesn't found in database! ");
        }
    }
    
}
