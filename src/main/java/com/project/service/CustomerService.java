package com.project.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import com.project.dto.LoginDto;
import com.project.entities.Customer;
import com.project.exception.CustomerNotFoundException;
import com.project.repository.CustomerRepository;
import com.project.response.AppResponse;
import com.project.util.JWTUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CustomerService {
    
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AppResponse response;

    @Autowired
    private JWTUtil util;

    public AppResponse registerCustomer(Customer customer){
        String hashedPassword = new BCryptPasswordEncoder().encode(customer.getPassword());
        customer.setCreated_at(new Date());
        customer.setPassword(hashedPassword);
        response.setMessage("Successfully add your data into database !");
        response.setCode(201);
        response.setSuccess(true);
        Map<String, Object> data = new HashMap<>();
        data.put("data", customer);
        response.setData(data);
        customerRepository.save(customer);
        return response;
    }

    public AppResponse login(LoginDto login) throws CustomerNotFoundException{
        Optional<Customer> customer = customerRepository.findByEmail(login.getEmail());
        if(customer.isEmpty()){
            response.setSuccess(false);
            response.setCode(401);
            response.setMessage("Customer doesn't found in database! ");
            response.setData(null);
            return response;
        }
        String password = customer.get().getPassword();
        if(new BCryptPasswordEncoder().matches(login.getPassword(), password)){
            Map<String, Object> data = new HashMap<>();
            data.put("email", login.getEmail());
            data.put("password", login.getPassword());
            data.put("token", util.generateToken(customer.get()));
            response.setCode(200);
            response.setMessage("Authenticated! ");
            response.setSuccess(true);
            response.setData(data);
            return response;
        } else {
            response.setSuccess(false);
            response.setCode(401);
            response.setMessage("Customer doesn't found in database! ");
            response.setData(null);
            return response;
        }
    }

    public Customer getCustomerById(long id){
        return customerRepository.findById(id).get();
    }

    public Iterable<Customer> getAllCustomer(){
        return customerRepository.findAll();
    }

    public AppResponse deleteCustomerById(long id){
        Optional<Customer> customer = customerRepository.findById(id);
        if(customer.isEmpty()){
            response.setMessage("Customer doesn't exist in database !");
            response.setCode(404);
            response.setSuccess(false);
            return response;
        } else {
            customerRepository.delete(customer.get());
            response.setMessage("Success delete the user !");
            response.setCode(200);
            response.setSuccess(true);
            return response;
        }
    }

    public AppResponse updateCustomer(long id, Customer bodyCustomer){
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
