package com.project.repository;

import java.util.Optional;

import com.project.entities.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{
    Optional<Customer> findByName(String name);
    Optional<Customer> findByEmail(String email);
}
