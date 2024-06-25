package com.retail.warehouse_management.repository;

import com.retail.warehouse_management.entity.Article;
import com.retail.warehouse_management.entity.Product;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
public class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @BeforeEach
    public void loadProducts(){
        Product product1 = new Product("Product A", new ArrayList<>(), 21);
        Product product2 = new Product("Product B", new ArrayList<>(), 30);
        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);
        productRepository.saveAll(products);
    }

    @Test
    public void testGetAllProducts(){
        List<Product> productsFromInventory = productRepository.findAll();
        assertThat(!productsFromInventory.isEmpty()).isTrue();
        assertThat(productsFromInventory.size()).isEqualTo(2);
    }

    @Test
    public void testFindByProductName(){
        List<Product> productsFromInventory = productRepository.findByProductName("Product A");
        assertThat(!productsFromInventory.isEmpty()).isTrue();
        assertThat(productsFromInventory.size()).isEqualTo(1);
        assertThat(productsFromInventory.get(0).getProductPrice()).isEqualTo(21);
    }

}
