package com.project.security;

import com.project.security.filter.AuthenticationFilter;
import com.project.security.filter.JWTFilter;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterAt(new AuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new JWTFilter(), UsernamePasswordAuthenticationFilter.class);

        http
        .authorizeRequests()
        .mvcMatchers("/customer/**", "/admin/**").authenticated()
        .anyRequest()
        .permitAll();

        http.formLogin().disable();
        http.csrf().disable();
        
        http.cors(corsConfigurer -> {
        	CorsConfigurationSource src = request -> {
        		CorsConfiguration config = new CorsConfiguration();
        		config.setAllowedOrigins(List.of("*"));
        		config.setAllowedHeaders(List.of("*"));
        		config.setAllowedMethods(List.of("*"));
        		return config;
        	};
        	corsConfigurer.configurationSource(src);
        });
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
            .mvcMatchers("/customer/login", "/customer/register", "/api/refresh", "/admin/create", "/admin/login");
    }
    
}
