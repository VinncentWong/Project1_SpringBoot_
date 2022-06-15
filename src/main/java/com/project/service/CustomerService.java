package com.project.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import com.project.dto.LoginDto;
import com.project.entities.Book;
import com.project.entities.Customer;
import com.project.exception.BookNotFoundException;
import com.project.exception.CustomerNotFoundException;
import com.project.repository.BookRepository;
import com.project.repository.CustomerRepository;
import com.project.response.AppResponse;
import com.project.util.JWTUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CustomerService {
    
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AppResponse response;

    @Autowired
    private JWTUtil util;

    public ResponseEntity<AppResponse> registerCustomer(Customer customer){
        String hashedPassword = new BCryptPasswordEncoder().encode(customer.getPassword());
        customer.setCreated_at(new Date());
        customer.setPassword(hashedPassword);
        response.setMessage("Successfully add your data into database !");
        response.setSuccess(true);
        Map<String, Object> data = new HashMap<>();
        data.put("data", customer);
        response.setData(data);
        customerRepository.save(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<AppResponse> login(LoginDto login) throws CustomerNotFoundException{
        Optional<Customer> customer = customerRepository.findByEmail(login.getEmail());
        if(customer.isEmpty()){
            response.setSuccess(false);
            response.setMessage("Customer doesn't found in database! ");
            response.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        String password = customer.get().getPassword();
        if(new BCryptPasswordEncoder().matches(login.getPassword(), password)){
            Map<String, Object> data = new HashMap<>();
            data.put("email", login.getEmail());
            data.put("password", login.getPassword());
            data.put("token", util.generateToken(customer.get()));
            data.put("refresh token", new JWTUtil().getSECRET_REFRESH_TOKEN_CUSTOMER());
            response.setMessage("Authenticated! ");
            response.setSuccess(true);
            response.setData(data);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            response.setSuccess(false);
            response.setMessage("Customer doesn't found in database! ");
            response.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public ResponseEntity<AppResponse> getCustomerById(long id) throws CustomerNotFoundException{
    	Map<String, Object> data = new HashMap<>();
    	data.put("data", customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException()));
    	return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public ResponseEntity<AppResponse> getAllCustomer(){
        Map<String, List<Customer>> data = new HashMap<>();
        data.put("data", customerRepository.findAll());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public ResponseEntity<AppResponse> deleteCustomerById(long id){
        Optional<Customer> customer = customerRepository.findById(id);
        if(customer.isEmpty()){
            response.setMessage("Customer doesn't exist in database !");
            response.setSuccess(false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } else {
            customerRepository.delete(customer.get());
            response.setMessage("Success delete the user !");
            response.setSuccess(true);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    public ResponseEntity<AppResponse> updateCustomer(long id, Customer bodyCustomer){
        Optional<Customer> customer = customerRepository.findById(id);
        if(customer.isEmpty()){
            response.setMessage("Customer doesn't exist in database !");
            response.setSuccess(false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
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
            response.setSuccess(true);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    public ResponseEntity<AppResponse> getBookById(Long id) throws BookNotFoundException{
        Optional<Book> book = bookRepository.findById(id);
        if(book.isEmpty()){
            throw new BookNotFoundException("Book data doesn't found! ");
        }
        Map<String, Object> data = new HashMap<>();
        data.put("data", book.get());
        response.setMessage("Succesfully update Book data! ");
        response.setSuccess(true);
        response.setData(data);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public ResponseEntity<AppResponse> getBook() throws BookNotFoundException{
        List<Book> book = bookRepository.findAll();
        if(book.isEmpty()){
            throw new BookNotFoundException("Book data doesn't found! ");
        }
        Map<String, Object> data = new HashMap<>();
        data.put("data", book);
        response.setMessage("Succesfully update Book data! ");
        response.setSuccess(true);
        response.setData(data);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
