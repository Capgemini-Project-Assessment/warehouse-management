package com.retail.warehouse_management.service;

import com.retail.warehouse_management.entity.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    public List<Product> getAllProductsWithQuantity();

    String sellProduct(String productName, Integer integer) throws Exception;
}
