package com.retail.warehouse_management.utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.retail.warehouse_management.dto.ProductInventory;
import com.retail.warehouse_management.exceptions.ErrorResponse;
import com.retail.warehouse_management.exceptions.FileLoadException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Component
public class FileLoad {
    public FileLoad() {
    }

    public ProductInventory loadInventoryArticlesFromFile(String inventoryFileName){
        File file = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            file = ResourceUtils.getFile(inventoryFileName);
            if (!file.exists()) {
                file = ResourceUtils.getFile("classpath:" + inventoryFileName);
            }
            String json = new String(Files.readAllBytes(file.toPath()));
            return mapper.readValue(json, ProductInventory.class);
        } catch (IOException e) {
            throw new FileLoadException(new ErrorResponse("Error while loading inventory data from json file", HttpStatus.BAD_REQUEST));
        }
    }

    public ProductInventory loadProductsFromFile(String productFileName){
        File file = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            file = ResourceUtils.getFile(productFileName);
            if (!file.exists()) {
                file = ResourceUtils.getFile("classpath:" + productFileName);
            }
            String json = new String(Files.readAllBytes(file.toPath()));
            return mapper.readValue(json, ProductInventory.class);
        } catch (IOException e) {
            throw new FileLoadException(new ErrorResponse("Error while loading product data from json file", HttpStatus.BAD_REQUEST));
        }

    }
}
