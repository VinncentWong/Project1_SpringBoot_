package com.project.security;

import com.project.security.filter.AuthenticationFilter;
import com.project.security.filter.JWTFilter;
import com.project.security.provider.JWTProvider;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(new JWTProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterAt(new AuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new JWTFilter(), UsernamePasswordAuthenticationFilter.class);

        http
        .authorizeRequests()
        .mvcMatchers("/customer/**").hasRole("CUSTOMER")
        .mvcMatchers("/admin/**").hasRole("ADMIN")
        .anyRequest()
        .permitAll();

        http.formLogin().disable();
        http.csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
            .mvcMatchers("/customer/login", "/customer/register", "/api/refresh", "/admin/create", "admin/login");
    }
    
}
