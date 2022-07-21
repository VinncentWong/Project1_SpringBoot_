package com.controller;

import com.google.gson.Gson;
import com.project.controller.AdminController;
import com.project.entities.Admin;
import com.project.response.AppResponse;
import com.project.service.AdminService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
@ExtendWith(MockitoExtension.class)
@WebMvcTest(AdminController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AdminControllerUT {
	
	private Logger log = LoggerFactory.getLogger(AdminControllerUT.class);
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminService adminService;
    
    Gson gson = new Gson();
    
    @Test
    public void adminShouldRegistered() throws Exception{
        Admin admin = new Admin();
        Map<String, Object> data = new HashMap<>();
        admin.setEmail("A@gmail.com");
        admin.setId(1L);
        admin.setName("Percobaan");
        admin.setPassword("Password");
        data.put("data", admin);
        
        when(adminService.createAdmin(admin)).thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(new AppResponse("Success", true, data)));
        
        log.info("Use when static method = " + gson.toJson(ResponseEntity.status(HttpStatus.CREATED).body(new AppResponse("Success", true, data))));
        log.info("Admin to JSON = " + gson.toJson(admin));
        log.info("Response to JSON = " + gson.toJson(new AppResponse("Success", true, data)));
        
        String jsonContent = gson.toJson(admin);
        RequestBuilder request = MockMvcRequestBuilders
        		.post("/admin/create")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(jsonContent)
        		.accept(MediaType.APPLICATION_JSON);
        
        MvcResult result = mockMvc.perform(request).andReturn();
        log.info("Perform request result : " + result.getResponse().getContentAsString());
        log.info("Call createadmin method result : " + gson.toJson(adminService.createAdmin(admin).getBody()));
        JSONAssert.assertEquals(result.getResponse().getContentAsString(), gson.toJson(adminService.createAdmin(admin).getBody()), false);
    }
}
