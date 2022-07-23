package com.project.security;

import com.project.security.filter.AuthenticationFilter;
import com.project.security.filter.JWTFilter;
import com.project.security.provider.JWTProvider;

import java.util.List;

import javax.servlet.Filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.RegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
	
	private Logger log = LoggerFactory.getLogger(SecurityConfig.class);
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	log.info("sblm penambahan filter");
        http.addFilterAt(new AuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(filter(), UsernamePasswordAuthenticationFilter.class);
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
    
    @Bean(value = {"provider"})
    public JWTProvider provider() {
    	return new JWTProvider();
    }

    @Bean
    public JWTFilter filter() {
    	return new JWTFilter();
    }
    
    @Bean
    public RegistrationBean jwtAuthFilterRegister(JWTFilter filter) {
    	log.info("disable filter");
        FilterRegistrationBean<JWTFilter> registrationBean = new FilterRegistrationBean<JWTFilter>(filter);
        registrationBean.setEnabled(false);
        return registrationBean;
    }
}
