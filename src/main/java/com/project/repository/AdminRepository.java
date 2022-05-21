package com.project.repository;

import java.util.Optional;

import com.project.entities.Admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface AdminRepository extends JpaRepository<Admin, Long>{
    Optional<Admin> findByName(String name);
    Optional<Admin> findByEmail(String email);    
}
