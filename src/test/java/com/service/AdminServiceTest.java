package com.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.project.entities.Admin;
import com.project.repository.AdminRepository;
import com.project.repository.BookRepository;
import com.project.service.AdminService;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {
	
	@MockBean
	private AdminRepository adminRepository;
	
	@MockBean
	private BookRepository bookRepository;
	
	@InjectMocks
	private AdminService adminService; // create admin service object and injects the mockbean
	
	private Admin admin;
	
	@BeforeAll
	public void init() {
		this.admin = new Admin();
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
		when(adminRepository.save(admin)).thenReturn(admin);
		verify(adminRepository).save(admin);
	}
}
