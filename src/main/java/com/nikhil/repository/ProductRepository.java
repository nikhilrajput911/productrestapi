package com.nikhil.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nikhil.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
