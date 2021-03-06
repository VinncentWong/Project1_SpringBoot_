package com.project.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.project.dto.LoginDto;
import com.project.entities.Admin;
import com.project.entities.Book;
import com.project.exception.AdminNotFoundException;
import com.project.exception.BookNotFoundException;
import com.project.exception.PasswordDoesntMatchException;
import com.project.repository.AdminRepository;
import com.project.repository.BookRepository;
import com.project.response.AppResponse;
import com.project.util.JWTUtil;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AppResponse response;

    @Autowired
    private BCryptPasswordEncoder bcrypt;

    @Autowired
    private JWTUtil util;
    
    public ResponseEntity<AppResponse> createAdmin(Admin admin){
        admin.setPassword(bcrypt.encode(admin.getPassword()));
        admin.setCreated_at(new Date());
        adminRepository.save(admin);
        Map<String, Object> data = new HashMap<>();
        data.put("data", admin);
        response.setMessage("Succesfully add Admin data! ");
        response.setSuccess(true);
        response.setData(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<AppResponse> login(LoginDto bodyLogin) throws AdminNotFoundException, PasswordDoesntMatchException{
        Optional<Admin> admin = adminRepository.findByEmail(bodyLogin.getEmail());
        if(admin.isEmpty()){
            throw new AdminNotFoundException("Admin data doesn't found in database! ");
        }
        String password = admin.get().getPassword();
        if(bcrypt.matches(bodyLogin.getPassword(), password)){
            Map<String, Object> data = new HashMap<>();
            String token = util.generateToken(admin.get());
            data.put("data", admin);
            data.put("token", token);
            data.put("refresh token", new JWTUtil().getSECRET_REFRESH_TOKEN_ADMIN());
            response.setMessage("Authenticated! ");
            response.setSuccess(true);
            response.setData(data);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            throw new PasswordDoesntMatchException("Password doesnt match! ");
        }
    }
    public ResponseEntity<AppResponse> getAdminById(Long id) throws AdminNotFoundException{
        Optional<Admin> admin = adminRepository.findById(id);
        if(admin.isEmpty()){
            throw new AdminNotFoundException("Admin data doesn't found in database! ");
        }
        Map<String, Object> data = new HashMap<>();
        data.put("data", admin);
        response.setMessage("Succesfully find Admin data! ");
        response.setSuccess(true);
        response.setData(data);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public ResponseEntity<AppResponse> deleteAdmin(Long id) throws AdminNotFoundException, Exception{
        Optional<Admin> admin = adminRepository.findById(id);
        if(admin.isEmpty()){
            throw new AdminNotFoundException("Admin data doesn't found in database! ");
        }
        adminRepository.deleteById(id);
        response.setMessage("Succesfully delete Admin data! ");
        response.setSuccess(true);
        response.setData(null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public ResponseEntity<AppResponse> updateAdmin(Long id, Admin bodyAdmin) throws AdminNotFoundException{
        Optional<Admin> admin = adminRepository.findById(id);
        if(admin.isEmpty()){
            throw new AdminNotFoundException("Admin data doesn't found in database! ");
        }
        if(bodyAdmin.getName() != null){
            admin.get().setName(bodyAdmin.getName());
        }
        if(bodyAdmin.getPassword() != null){
            admin.get().setPassword(new BCryptPasswordEncoder().encode(bodyAdmin.getPassword()));
        }
        admin.get().setUpdated_at(new Date());
        adminRepository.save(admin.get());
        Map<String, Object> data = new HashMap<>();
        data.put("data", admin);
        response.setMessage("Succesfully update Book data! ");
        response.setSuccess(true);
        response.setData(data);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    public ResponseEntity<AppResponse> addBook(Book book){
        book.setCreated_at(new Date());
        bookRepository.save(book);
        Map<String, Object> data = new HashMap<>();
        data.put("data", book);
        response.setMessage("Succesfully add Book data! ");
        response.setSuccess(true);
        response.setData(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<AppResponse> deleteBook(Long id) throws BookNotFoundException{
        Optional<Book> book = bookRepository.findById(id);
        if(book.isEmpty()){
            throw new BookNotFoundException("Book data doesn't found! ");
        }
        bookRepository.delete(book.get());
        response.setMessage("Succesfully delete Book data! ");
        response.setSuccess(true);
        response.setData(null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public ResponseEntity<AppResponse> updateBook(Long id, Book bodyBook) throws BookNotFoundException{
        Optional<Book> book = bookRepository.findById(id);
        if(book.isEmpty()){
            throw new BookNotFoundException("Book data doesn't found! ");
        }
        if(bodyBook.getName() != null){
            book.get().setName(bodyBook.getName());
        }
        if(bodyBook.getPrice() != book.get().getPrice() && bodyBook.getPrice() != 0){
            book.get().setPrice(bodyBook.getPrice());
        }
        if(bodyBook.getStock() != book.get().getStock() && bodyBook.getStock() != 0){
            book.get().setStock(bodyBook.getStock());
        }
        if(bodyBook.getSynopsis() != null){
            book.get().setSynopsis(bodyBook.getSynopsis());
        }
        book.get().setUpdated_at(new Date());
        bookRepository.save(book.get());
        Map<String, Object> data = new HashMap<>();
        data.put("data", book.get());
        response.setMessage("Succesfully update Book data! ");
        response.setSuccess(true);
        response.setData(data);
        return ResponseEntity.status(HttpStatus.OK).body(response);
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
        response.setMessage("Succesfully get Book data! ");
        response.setSuccess(true);
        response.setData(data);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
