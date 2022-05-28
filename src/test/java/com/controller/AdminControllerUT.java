package com.controller;

import com.project.controller.AdminController;
import com.project.entities.Admin;
import com.project.response.AppResponse;
import com.project.service.AdminService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.util.HashMap;
import java.util.Map;
@ExtendWith(MockitoExtension.class)
@WebMvcTest(AdminController.class)
public class AdminControllerUT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminService adminService;

    private AppResponse response = new AppResponse();
    
    @Test
    public void adminShouldRegistered() throws Exception{
        Map<String, Object> map = new HashMap<>();
        map.put("data", new Admin(1L, "Admin A", "AAAAAA", "AdminA@gmail.com"));
        response.setCode(200);
        response.setData(map);
        response.setSuccess(true);
        response.setMessage("Success register Admin! ");
        when(adminService.createAdmin(new Admin(null, null, null, null))).thenReturn(response);
        RequestBuilder request = MockMvcRequestBuilders
                                .post("/admin/login")
                                .content("\"code\" : 200, \"success\" : true, \"message\" : \"Success Register Admin! \", \"data\" : {\"id\" : 1, \"name\" : \"Admin A\", \"password\" : \"AAAAAA\", \"email\" : \"AdminA@gmail.com\"D}}")
                                .accept(MediaType.APPLICATION_JSON);
        
        MvcResult result = mockMvc
                            .perform(request)
                            .andExpect(status().isBadRequest())
                            .andReturn();
        JSONAssert.assertEquals(response.toString(), result.toString(), false);
    }
}
