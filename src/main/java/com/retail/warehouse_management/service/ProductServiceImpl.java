package com.retail.warehouse_management.service;

import com.retail.warehouse_management.dto.ProductDto;
import com.retail.warehouse_management.dto.ProductInventory;
import com.retail.warehouse_management.entity.Product;
import com.retail.warehouse_management.entity.ProductArticle;
import com.retail.warehouse_management.exceptions.ErrorResponse;
import com.retail.warehouse_management.exceptions.ProductNotFoundException;
import com.retail.warehouse_management.repository.ProductRepository;
import com.retail.warehouse_management.utility.FileLoad;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.retail.warehouse_management.constants.Constants.*;

@Service
public class ProductServiceImpl implements ProductService{
    @Value("${warehouse.products.file}")
    private String productFile;
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private Map<String, ProductDto> productsMap = new HashMap<>();

    private final FileLoad fileLoad;
    private final ProductRepository productRepository;
    private final InventoryService inventoryService;

    public ProductServiceImpl(FileLoad fileLoad, ProductRepository productRepository, InventoryService inventoryService) {
        this.fileLoad = fileLoad;
        this.productRepository = productRepository;
        this.inventoryService = inventoryService;
    }

    @PostConstruct
    public void init() {
        log.info("Loading products...");
        ProductInventory productInventory = fileLoad.loadProductsFromFile(productFile);
        productsMap = productInventory.products().stream().collect(Collectors.toMap(ProductDto::getProductName, a -> a));
        if (!productsMap.isEmpty()) {
            productRepository.saveAll(mapProductDtoToEntity(productsMap.values()));
            log.info("Loaded {} products", productsMap.size());
        } else {
            log.warn("No product loaded !");
        }
    }

    public Map<String, ProductDto> mapOfProducts(){
        return productsMap;
    }

    public List<Product> mapProductDtoToEntity(Collection<ProductDto> products) {
        ModelMapper modelMapper = new ModelMapper();
        TypeMap<ProductDto, Product> productPropertyMapper = modelMapper.createTypeMap(ProductDto.class, Product.class);
        productPropertyMapper.addMappings(mappper -> mappper.skip(Product::setProductId));

        return  products.stream().map(p -> modelMapper.map(p, Product.class)).collect(Collectors.toList());
    }

    public List<Product> getAllProductsWithQuantity() {
        List<Product> productList = productRepository.findAll();
        if(CollectionUtils.isEmpty(productList)){
            log.info(PRODUCT_NOT_AVAILABLE);
            throw new ProductNotFoundException(new ErrorResponse(PRODUCT_NOT_AVAILABLE, HttpStatus.NOT_FOUND));
        }
        List<Product> availableProductList = productList.stream().filter(p -> checkProductAvailability(p,1)).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(availableProductList)){
            log.info(PRODUCT_NOT_AVAILABLE);
            throw new ProductNotFoundException(new ErrorResponse(PRODUCT_NOT_AVAILABLE, HttpStatus.NOT_FOUND));
        }

        availableProductList.forEach(product -> {
            calculateProductQuantityInStock(product);
        });
        return availableProductList;
    }

    /**
     * Set quantity of the product available in inventory stock
     * @param product
     */
    private void calculateProductQuantityInStock(Product product) {
        setProductArticlesStock(product);
        boolean isArticleStockAvailable = true;
        int articleCount = product.getArticles().size();
        int productQuantity = 0;

        while(isArticleStockAvailable){
            int checkedArticles = 0;
            for (ProductArticle article : product.getArticles()){
                checkedArticles++;
                int stockRemaining = article.getArticleInventoryStock() - article.getArticleAmount();
                article.setArticleInventoryStock(stockRemaining);
                if(stockRemaining < 0){
                    isArticleStockAvailable = false;
                    break;
                } else if (checkedArticles == articleCount)
                    productQuantity++;

            }
        }
        product.setQuantityInStock(productQuantity);
    }

    public String sellProduct(String productName, Integer quantity){
        Product product = productRepository.findByProductName(productName)
                                            .stream()
                                            .findFirst()
                                            .orElseThrow(() -> new ProductNotFoundException(new ErrorResponse(String.format("Sorry, this product - %s is not available in inventory for sell", productName), HttpStatus.NOT_FOUND)));

        //With the product derived above based on product name and with quantity,
        // check if enough constituent articles are present in inventory to make a sell
        if(checkProductAvailability(product,quantity)) {
            updateInventory(product, quantity);
            productRepository.delete(product);
            return PRODUCT_SOLD_SUCCESSFULLY;
        } else{
            throw new ProductNotFoundException(new ErrorResponse(String.format(PRODUCT_NOT_SOLD, productName), HttpStatus.NOT_FOUND));
        }

    }

    /**
     * Determine and set stock of product article that is available in inventory
     * @param product
     */
    void setProductArticlesStock(Product product){
        product.getArticles()
                .forEach(productArticle -> productArticle.setArticleInventoryStock(inventoryService.getArticleStock(productArticle.getArticleId())));
    }

    /**
     * Verify and set product availability
     * @param product
     * @return Boolean
     */
    public boolean checkProductAvailability(Product product, int quantity) {
        boolean isAvailable = true;
        for (ProductArticle productArticle : product.getArticles()) {
            if (!isProductArticleAvailable(productArticle,quantity)) {
                return false;
            }
        }
        product.setAvailable(isAvailable); //Is this being used ?
        return true;
    }

    /**
     * Verify productArticles availability with the current inventory
     * @param productArticle
     * @return boolean
     */
    private boolean isProductArticleAvailable(ProductArticle productArticle,int quantity) {
        return inventoryService.getArticleStock(productArticle.getArticleId()) >= quantity*productArticle.getArticleAmount();
    }

    /**
     * Update the inventory with new article quantities after product has been purchased
     * @param product
     */
    private void updateInventory(Product product, int quantity) {
        product.getArticles()
                .forEach(productArticle -> inventoryService.updateArticleStock(productArticle.getArticleId(), productArticle.getArticleAmount()*quantity));
    }
}
