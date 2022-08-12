package com.project.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/check")
public class CheckHealth {
	
	public String checkHealth() {
		return "OK";
	}
}
