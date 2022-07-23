package com.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.project.entities.Admin;
import com.project.repository.AdminRepository;
import com.project.repository.BookRepository;
import com.project.response.AppResponse;
import com.project.service.AdminService;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {
	
	@Mock
	private AdminRepository adminRepository;
	
	@Mock
	private BookRepository bookRepository;

	@Mock
	private AppResponse response;
	
	@InjectMocks
	private AdminService adminService; // create admin service object and injects the mockbean
	
	@Mock
	private BCryptPasswordEncoder bcrypt;

	@Mock
	private ResponseEntity responseEntity; 

	private  Admin admin;

	private Logger log = LoggerFactory.getLogger(AdminServiceTest.class);
	
	public void init() {
		admin = new Admin();
		admin.setCreated_at(new Date());
		admin.setDeleted_at(null);
		admin.setEmail("centwong@gmail.com");
		admin.setId(1L);
		admin.setName("centwong");
		admin.setPassword("centwong");
		admin.setUpdated_at(null);
	}
	
	@Test
	@DisplayName("admin should be created")
	public void adminShouldBeCreated() {
		init();
		when(adminRepository.save(admin)).thenReturn(admin);
		when(bcrypt.encode(admin.getPassword())).thenReturn(admin.getPassword());
		when(responseEntity.status(anyInt())).thenReturn(responseEntity.status(201));
		var response = adminService.createAdmin(admin);
		var mapData = response.getBody();
		verify(adminRepository).save(admin);
		assertEquals(mapData.getData(), admin);
	}
}
