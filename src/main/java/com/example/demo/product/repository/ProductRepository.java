package com.example.demo.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.product.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{

}
