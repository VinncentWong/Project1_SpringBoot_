package com.project.service;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import com.project.dto.LoginDto;
import com.project.entities.Customer;
import com.project.exception.CustomerNotFoundException;
import com.project.repository.CustomerRepository;
import com.project.response.AuthResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CustomerService {
    
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AuthResponse response;

    public AuthResponse registerCustomer(Customer customer){
        String hashedPassword = new BCryptPasswordEncoder().encode(customer.getPassword());
        customer.setCreated_at(new Date());
        customer.setPassword(hashedPassword);
        response.setMessage("Successfully add your data into database !");
        response.setCode(201);
        response.setSuccess(true);
        return response;
    }

    public AuthResponse login(LoginDto bodyCustomer) throws CustomerNotFoundException{
        Optional<Customer> customer = customerRepository.findByEmail(bodyCustomer.getEmail());
        if(customer.isEmpty()){
            throw new CustomerNotFoundException("Customer doesn't exist in database !");
        }
        boolean valid = new BCryptPasswordEncoder().matches(bodyCustomer.getPassword(), customer.get().getPassword());
        if(valid){
            response.setMessage("authenticated !");
            response.setCode(200);
            response.setSuccess(true);
            return response;
        } else {
            response.setMessage("unauthenticated !");
            response.setCode(401);
            response.setSuccess(false);
            return response;
        }
    }

    public Customer getCustomerById(long id){
        return customerRepository.findById(id).get();
    }

    public Iterable<Customer> getAllCustomer(){
        return customerRepository.findAll();
    }

    public AuthResponse deleteCustomerById(long id){
        Optional<Customer> customer = customerRepository.findById(id);
        if(customer.isEmpty()){
            response.setMessage("Customer doesn't exist in database !");
            response.setCode(404);
            response.setSuccess(false);
            return response;
        } else {
            response.setMessage("Success delete the user !");
            response.setCode(200);
            response.setSuccess(true);
            return response;
        }
    }

    public AuthResponse updateUser(long id, Customer bodyCustomer){
        Optional<Customer> customer = customerRepository.findById(id);
        if(customer.isEmpty()){
            response.setMessage("Customer doesn't exist in database !");
            response.setCode(404);
            response.setSuccess(false);
            return response;
        } else {
            if(customer.get().getName() != null){
                customer.get().setName(bodyCustomer.getName());
            }
            if(customer.get().getPassword() != null){
                customer.get().setPassword(bodyCustomer.getPassword());
            }
            if(customer.get().getEmail() != null){
                customer.get().setEmail(bodyCustomer.getEmail());
            }
            customer.get().setUpdated_at(new Date());
            response.setMessage("Success update the user !");
            response.setCode(200);
            response.setSuccess(true);
            return response;
        }
    }
}
