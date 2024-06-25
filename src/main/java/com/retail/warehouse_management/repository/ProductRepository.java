package com.retail.warehouse_management.repository;

import com.retail.warehouse_management.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findAll();
    List<Product> findByProductName(String productName);
}
