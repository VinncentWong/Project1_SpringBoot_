package com.project.controller;

import javax.validation.Valid;

import com.project.dto.LoginDto;
import com.project.entities.Customer;
import com.project.exception.CustomerNotFoundException;
import com.project.response.AuthResponse;
import com.project.response.PropertyNullException;
import com.project.service.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    
    @Autowired
    private CustomerService customerService;

    @PostMapping("/register")
    public AuthResponse registerCustomer(@RequestBody @Valid Customer bodyCustomer){
        return customerService.registerCustomer(bodyCustomer);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginDto bodyCustomer) throws CustomerNotFoundException, PropertyNullException{
        if(bodyCustomer.getEmail() == null || bodyCustomer.getPassword() == null){
            throw new PropertyNullException("Property can't be null !");
        }
        return customerService.login(bodyCustomer);
    }

    @GetMapping("/get/{id}")
    public Customer getCustomerById(@PathVariable Long id){
        return customerService.getCustomerById(id);
    }

    @GetMapping("/get")
    public Iterable<Customer> getAllCustomer(){
        return customerService.getAllCustomer();
    }

    @DeleteMapping("/delete/{id}")
    public AuthResponse deleteCustomer(@PathVariable Long id){
        return customerService.deleteCustomerById(id);
    }


}
