package com.project.entities;

import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import lombok.Data;

@Entity
@Data
public class Invoice{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    private String bookName;

    @NotNull
    @PositiveOrZero
    private int price;

    @NotNull
    @PositiveOrZero
    private int totalPrice;
    
    @NotNull
    private LocalTime created_at;

    private LocalTime updated_at;
    
    private LocalTime deleted_at;
}