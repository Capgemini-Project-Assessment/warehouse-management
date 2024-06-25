package com.retail.warehouse_management.controller;

import com.retail.warehouse_management.entity.Article;
import com.retail.warehouse_management.entity.Product;
import com.retail.warehouse_management.service.InventoryService;
import com.retail.warehouse_management.service.ProductService;
import com.retail.warehouse_management.utility.StringUtlity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static com.retail.warehouse_management.constants.Constants.API_VERSION_1;
import static com.retail.warehouse_management.constants.Constants.PRODUCT_EMPTY;

@RestController
@RequestMapping(API_VERSION_1)
public class ProductController {
    private final ProductService productService;
    private final InventoryService inventoryService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public ProductController(ProductService productService, InventoryService inventoryService){
        this.productService = productService;
        this.inventoryService = inventoryService;
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProductsWithQuantity() {
        log.info("Getting all products with their quantity");
        return new ResponseEntity<>(productService.getAllProductsWithQuantity(), HttpStatus.OK);
    }

    /**
     * Sell product and update the inventory accordingly
     * @param productName, quantity(optional)
     * @return String
     */
    @DeleteMapping(value = {"/sell/product/{productName}", "/sell/product/{productName}/{quantity}"})
    public ResponseEntity<String> sellProductByName(@PathVariable String productName,
                                                     @PathVariable(required = false) Optional<Integer> quantity) throws Exception {
        log.info("Sell product by name");
        if (!StringUtlity.isNotEmpty(productName)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, PRODUCT_EMPTY);
        }
        return new ResponseEntity<>(productService.sellProduct(productName, quantity.orElse(1)), HttpStatus.OK);
    }

    /**
     * Get the list of all articles and details from inventory
     * @return Artcles
     */
    @GetMapping("/articles")
    public ResponseEntity<List<Article>> getAllArticles() {
        log.info("Fetching all the articles and their details from inventory");
        return new ResponseEntity<>(inventoryService.getAllArticles(), HttpStatus.OK);
    }

    @GetMapping("/article/{articleId}")
    public ResponseEntity<Article> getAllArticleById(@PathVariable int articleId) {
        log.info("Fetching the article and its details from inventory");
        return new ResponseEntity<>(inventoryService.getArticleById(articleId), HttpStatus.OK);
    }
}
