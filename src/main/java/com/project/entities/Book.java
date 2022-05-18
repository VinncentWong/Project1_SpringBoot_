package com.project.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String synopsis;

    @NotNull
    @PositiveOrZero
    private int stock;
    
    @NotNull
    @PositiveOrZero
    private int price;

    @NotNull
    private LocalTime created_at;

    private LocalTime updated_at;
    
    private LocalTime deleted_at;
}
