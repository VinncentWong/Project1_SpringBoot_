package com.project.controller;

import javax.validation.Valid;

import com.project.dto.LoginDto;
import com.project.entities.Customer;
import com.project.exception.BookNotFoundException;
import com.project.exception.CustomerNotFoundException;
import com.project.exception.PropertyNullException;
import com.project.response.AppResponse;
import com.project.service.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
    public AppResponse registerCustomer(@RequestBody @Valid Customer bodyCustomer){
        return customerService.registerCustomer(bodyCustomer);
    }

    @PostMapping("/login")
    public AppResponse login(@RequestBody @Valid LoginDto bodyLogin) throws CustomerNotFoundException, PropertyNullException{
        return customerService.login(bodyLogin);
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
    public AppResponse deleteCustomer(@PathVariable Long id){
        return customerService.deleteCustomerById(id);
    }

    @PatchMapping("/update/{id}")
    public AppResponse updateCustomer(@PathVariable Long id, @RequestBody Customer customer){
        return customerService.updateCustomer(id, customer);
    }

    @GetMapping("/getbook/{id}")
    public AppResponse getBookById(@PathVariable Long id) throws BookNotFoundException{
        return customerService.getBookById(id);
    }

    @GetMapping("/getbooks")
    public AppResponse getBooks() throws BookNotFoundException{
        return customerService.getBook();
    }

    
}
