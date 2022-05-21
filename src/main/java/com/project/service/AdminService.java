package com.project.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.project.dto.LoginDto;
import com.project.entities.Admin;
import com.project.entities.Book;
import com.project.exception.AdminNotFoundException;
import com.project.exception.PasswordDoesntMatchException;
import com.project.repository.AdminRepository;
import com.project.repository.BookRepository;
import com.project.response.AppResponse;
import com.project.util.JWTUtil;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    
    public AppResponse createAdmin(Admin admin){
        adminRepository.save(admin);
        Map<String, Object> data = new HashMap<>();
        data.put("data", admin);
        response.setCode(200);
        response.setMessage("Succesfully add Admin data! ");
        response.setSuccess(true);
        response.setData(data);
        return response;
    }

    public AppResponse login(LoginDto bodyLogin) throws AdminNotFoundException, PasswordDoesntMatchException{
        Optional<Admin> admin = adminRepository.findByEmail(bodyLogin.getEmail());
        if(admin.isEmpty()){
            throw new AdminNotFoundException("Admin data doesn't found in database! ");
        }
        String password = admin.get().getPassword();
        if(new BCryptPasswordEncoder().matches(bodyLogin.getPassword(), password)){
            Map<String, Object> data = new HashMap<>();
            String token = new JWTUtil().generateToken(admin.get());
            data.put("data", admin);
            data.put("token", token);
            response.setCode(200);
            response.setMessage("Authenticated! ");
            response.setSuccess(true);
            response.setData(data);
            return response;
        } else {
            throw new PasswordDoesntMatchException("Password doesnt match! ");
        }
    }
    public AppResponse getAdminById(Long id) throws AdminNotFoundException{
        Optional<Admin> admin = adminRepository.findById(id);
        if(admin.isEmpty()){
            throw new AdminNotFoundException("Admin data doesn't found in database! ");
        }
        Map<String, Object> data = new HashMap<>();
        data.put("data", admin);
        response.setCode(200);
        response.setMessage("Succesfully find Admin data! ");
        response.setSuccess(true);
        response.setData(data);
        return response;
    }

    public AppResponse deleteAdmin(Long id) throws AdminNotFoundException, Exception{
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String token = auth.getPrincipal().toString();
        String subject = new JWTUtil().getSubject(token);
        Optional<Admin> admin = adminRepository.findById(id);
        if(admin.isEmpty()){
            throw new AdminNotFoundException("Admin data doesn't found in database! ");
        }
        if(!subject.equals(admin.get().getName())){
            throw new Exception("Can't delete another Admin data! ");
        }
        adminRepository.deleteById(id);
        response.setCode(200);
        response.setMessage("Succesfully delete Admin data! ");
        response.setSuccess(true);
        response.setData(null);
        return response;
    }

    public AppResponse addBook(Book book){
        bookRepository.save(book);
        Map<String, Object> data = new HashMap<>();
        data.put("data", book);
        response.setCode(200);
        response.setMessage("Succesfully add Book data! ");
        response.setSuccess(true);
        response.setData(data);
        return response;
    }
}