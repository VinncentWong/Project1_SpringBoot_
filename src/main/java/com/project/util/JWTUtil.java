package com.project.util;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Value;



public class JWTUtil implements Serializable{
    @Value("${jwt.secret}")
    private String SECRET_KEY;

}
