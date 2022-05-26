package com.project.repository;

import com.project.entities.WishList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface InvoiceRepository extends JpaRepository<WishList, Long> {}
