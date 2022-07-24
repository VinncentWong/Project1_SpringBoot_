package com.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.project.dto.LoginDto;
import com.project.entities.Admin;
import com.project.exception.AdminNotFoundException;
import com.project.exception.PasswordDoesntMatchException;
import com.project.repository.AdminRepository;
import com.project.repository.BookRepository;
import com.project.response.AppResponse;
import com.project.service.AdminService;
import com.project.util.JWTUtil;

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

	@MockBean
	private ResponseEntity<AppResponse> responseEntity; 

	@Mock
	private JWTUtil util;

	private  Admin admin;
	
	public void init() {
		admin = new Admin();
		admin.setCreated_at(new Date());
		admin.setDeleted_at(null);
		admin.setEmail("centwong@gmail.com");
		admin.setId(1L);
		admin.setName("centwong");
		admin.setPassword("centwong");
		admin.setUpdated_at(null);

		response = new AppResponse();
		Map<String, Object> map = new HashMap<>();
		map.put("data", admin);
		response.setMessage("success");
		response.setSuccess(true);
		response.setData(map);
	}
	
	@Test
	@DisplayName("admin should be created")
	public void adminShouldBeCreated() {
		// Assumption
		init();
		when(adminRepository.save(admin)).thenReturn(admin);
		when(bcrypt.encode(admin.getPassword())).thenReturn(admin.getPassword());
		// Call
		var response = adminService.createAdmin(admin);

		//Validation
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		verify(adminRepository, times(1)).save(admin);
	}

	@Test
	@DisplayName("admin should success login")
	public void adminShouldSuccessLogin() throws AdminNotFoundException, PasswordDoesntMatchException{
		init();
		Optional<Admin> optional = Optional.ofNullable(admin);
		when(adminRepository.findByEmail(admin.getEmail())).thenReturn(optional);
		when(bcrypt.matches(admin.getPassword(), optional.get().getPassword())).thenReturn(true);
		when(util.generateToken(admin)).thenReturn("key");
		LoginDto dto = new LoginDto();
		dto.setEmail(admin.getEmail());
		dto.setPassword(admin.getPassword());
		var mockResponse = adminService.login(dto);
		assertEquals(HttpStatus.OK, mockResponse.getStatusCode());
		verify(adminRepository, times(1)).findByEmail(admin.getEmail());
		verify(bcrypt, times(1)).matches(admin.getPassword(), optional.get().getPassword());
		verify(util, times(1)).generateToken(admin);
	}
}
