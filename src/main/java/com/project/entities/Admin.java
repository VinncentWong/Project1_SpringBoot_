package com.project.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
public class Admin {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    private String email;
    
    private Date created_at;

    private Date updated_at;
    
    private Date deleted_at;

    public Admin(Long id, @NotNull @NotEmpty String name, @NotNull @NotEmpty String password,
            @NotNull @NotEmpty String email) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
    }
}
