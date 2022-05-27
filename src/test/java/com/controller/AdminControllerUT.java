package com.controller;

import com.project.controller.AdminController;
import com.project.entities.Admin;
import com.project.service.AdminService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(AdminController.class)
public class AdminControllerUT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminService adminService;
    
    @Test
    public void adminShouldRegistered() throws Exception{
        when(adminService.createAdmin(new Admin())).thenReturn(new Admin(1L,"Admin A", "AdminA@gmail.com","AAAAAA"));
        RequestBuilder request = MockMvcRequestBuilders
                                .post("/admin/login")
                                .accept(MediaType.APPLICATION_JSON);
        mockMvc
            .perform(request)
            .andExpect(content().json("\"name\" : \"Admin A\", \"email\" : \"AdminA@gmail.com\", \"password\" : \"AAAAAA\""))
            .andReturn();
    }
}
