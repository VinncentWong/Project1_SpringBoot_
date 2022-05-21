package com.project.controller;

import com.project.response.AppResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    
    @PostMapping("/create")
    public AppResponse createAdmin(){

    }
}
