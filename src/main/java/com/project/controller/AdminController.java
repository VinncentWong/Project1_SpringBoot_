package com.project.controller;

import javax.validation.Valid;

import com.project.dto.LoginDto;
import com.project.entities.Admin;
import com.project.entities.Book;
import com.project.exception.AdminNotFoundException;
import com.project.exception.BookNotFoundException;
import com.project.exception.PasswordDoesntMatchException;
import com.project.response.AppResponse;
import com.project.service.AdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private AdminService adminService;

    @PostMapping("/create")
    public ResponseEntity<AppResponse> createAdmin(@RequestBody @Valid Admin admin){
        return adminService.createAdmin(admin);
    }

    @PostMapping("/login")
    public ResponseEntity<AppResponse> login(@Valid @RequestBody LoginDto bodyLogin) throws PasswordDoesntMatchException, AdminNotFoundException{
        return adminService.login(bodyLogin);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<AppResponse> getAdmin(@PathVariable Long id) throws AdminNotFoundException{
        return adminService.getAdminById(id);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<AppResponse> updateAdmin(@RequestBody Admin admin, @PathVariable Long id) throws AdminNotFoundException{
        return adminService.updateAdmin(id, admin);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<AppResponse> deleteAdmin(@PathVariable Long id) throws AdminNotFoundException, Exception{
        return adminService.deleteAdmin(id);
    }
    
    @PostMapping("/addbook")
    public ResponseEntity<AppResponse> addBook(@RequestBody @Valid Book book){
        return adminService.addBook(book);
    }

    @GetMapping("/getbook")
    public ResponseEntity<AppResponse> getBook() throws BookNotFoundException{
        return adminService.getBook();
    }

    @GetMapping("/getbook/{id}")
    public ResponseEntity<AppResponse> getBookById(@PathVariable Long id) throws BookNotFoundException{
        return adminService.getBookById(id);
    }

    @PatchMapping("/updatebook/{id}")
    public ResponseEntity<AppResponse> updateBook(@PathVariable Long id, @RequestBody Book book) throws BookNotFoundException{
        return adminService.updateBook(id, book);
    }

    @DeleteMapping("/deletebook/{id}")
    public ResponseEntity<AppResponse> deleteBook(@PathVariable Long id) throws BookNotFoundException{
        return adminService.deleteBook(id);
    }
    
}
