package com.retail.warehouse_management.Service;

import com.retail.warehouse_management.dto.ProductDto;
import com.retail.warehouse_management.entity.Article;
import com.retail.warehouse_management.entity.Product;
import com.retail.warehouse_management.entity.ProductArticle;
import com.retail.warehouse_management.exceptions.ProductNotFoundException;
import com.retail.warehouse_management.repository.InventoryRepository;
import com.retail.warehouse_management.repository.ProductRepository;
import com.retail.warehouse_management.service.InventoryServiceImpl;
import com.retail.warehouse_management.service.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static com.retail.warehouse_management.constants.Constants.PRODUCT_NOT_AVAILABLE;
import static com.retail.warehouse_management.constants.Constants.PRODUCT_SOLD_SUCCESSFULLY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @InjectMocks
    ProductServiceImpl productServiceImpl;

    @Mock
    InventoryServiceImpl inventoryServiceImpl;

    @Mock
    ProductRepository productRepository;

    @Test
    public void testMapProductDtoToEntity(){
        ProductDto productDto1 = new ProductDto("Chair", new ArrayList<>(), 12);
        ProductDto productDto2 = new ProductDto("Table", new ArrayList<>(), 15);
        List<ProductDto> productDtoList = new ArrayList<>(List.of(productDto1,productDto2));
        List<Product> productList = productServiceImpl.mapProductDtoToEntity(productDtoList);

        assertThat(productList).isNotEmpty();
        assertThat(productList.size()).isEqualTo(2);
    }

    @Test
    public void testGetAllProductsWithQuantity_Happy_Path(){
        Product product_chair = new Product("Chair", new ArrayList<>(List.of(new ProductArticle(1,2))), 12);
        Product product_table = new Product("Table", new ArrayList<>(List.of(new ProductArticle(2,4))), 15);
        when(productRepository.findAll()).thenReturn(List.of(product_chair,product_table));

//        Article article_arm_rests = new Article(1, "Arm rests", 8);
//        Article article_legs = new Article(2, "Legs", 12);
//        when(inventoryRepository.findAll()).thenReturn(List.of(article_arm_rests,article_legs));
//        when(inventoryRepository.findById(1)).thenReturn(Optional.of(article_arm_rests));
//        when(inventoryRepository.findById(2)).thenReturn(Optional.of(article_legs));

        when(inventoryServiceImpl.getArticleStock(1)).thenReturn(8);
        when(inventoryServiceImpl.getArticleStock(2)).thenReturn(12);

        List<Product> listOfProducts = productServiceImpl.getAllProductsWithQuantity();
        assertThat(listOfProducts).isNotEmpty();
        assertThat(listOfProducts.size()).isEqualTo(2);
        assertThat(listOfProducts.get(0).getQuantityInStock()).isEqualTo(4);//There can be 4 Chairs
        assertThat(listOfProducts.get(1).getQuantityInStock()).isEqualTo(3);//There can be 3 tables
    }

    @Test
    public void testGetAllProductsWithQuantity_only_one_Available(){
        Product product_chair = new Product("Chair", new ArrayList<>(List.of(new ProductArticle(1,8))), 12);
        Product product_table = new Product("Table", new ArrayList<>(List.of(new ProductArticle(2,4))), 15);
        when(productRepository.findAll()).thenReturn(List.of(product_chair,product_table));

        when(inventoryServiceImpl.getArticleStock(1)).thenReturn(4);
        when(inventoryServiceImpl.getArticleStock(2)).thenReturn(12);

        List<Product> listOfProducts = productServiceImpl.getAllProductsWithQuantity();
        assertThat(listOfProducts).isNotEmpty();
        assertThat(listOfProducts.size()).isEqualTo(1);
        assertThat(listOfProducts.get(0).getQuantityInStock()).isEqualTo(3);//Only table is available with quantity 3
    }

    @Test
    public void testGetAllProductsWithQuantity_no_product_Available(){
        Product product_chair = new Product("Chair", new ArrayList<>(List.of(new ProductArticle(1,8))), 12);
        Product product_table = new Product("Table", new ArrayList<>(List.of(new ProductArticle(2,12))), 15);
        when(productRepository.findAll()).thenReturn(List.of(product_chair,product_table));

        when(inventoryServiceImpl.getArticleStock(1)).thenReturn(4);
        when(inventoryServiceImpl.getArticleStock(2)).thenReturn(8);

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () -> productServiceImpl.getAllProductsWithQuantity());
        assertEquals(PRODUCT_NOT_AVAILABLE, exception.getErrorResponse().message());
    }

    @Test
    public void testSellProduct_Successful(){
        Product product_chair = new Product("Chair", new ArrayList<>(List.of(new ProductArticle(1,4))), 12);
        when(productRepository.findByProductName("Chair")).thenReturn(List.of(product_chair));
        when(inventoryServiceImpl.getArticleStock(1)).thenReturn(12);
        when(inventoryServiceImpl.updateArticleStock(1,4)).thenReturn(new Article(1,"Legs",8));

        assertThat(productServiceImpl.sellProduct("Chair",1)).isEqualTo(PRODUCT_SOLD_SUCCESSFULLY);
    }

    @Test
    public void testProductNotSold(){
        Product product_chair = new Product("Chair", new ArrayList<>(List.of(new ProductArticle(1,8))), 12);
        when(productRepository.findByProductName("Chair")).thenReturn(List.of(product_chair));
        when(inventoryServiceImpl.getArticleStock(1)).thenReturn(4);

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () -> productServiceImpl.sellProduct("Chair",1));
        assertEquals("Sorry, the product - Chair is not available in inventory for sell", exception.getErrorResponse().message());
    }

    @Test
    public void testCheckProductAvailability_Available(){
        when(inventoryServiceImpl.getArticleStock(1)).thenReturn(12);
        Product product_chair = new Product("Chair", new ArrayList<>(List.of(new ProductArticle(1,4))), 12);
        assertThat(productServiceImpl.checkProductAvailability(product_chair,1)).isTrue();
    }

    @Test
    public void testCheckProductAvailability_Not_Available(){
        when(inventoryServiceImpl.getArticleStock(1)).thenReturn(4);
        Product product_chair = new Product("Chair", new ArrayList<>(List.of(new ProductArticle(1,8))), 12);
        assertThat(productServiceImpl.checkProductAvailability(product_chair,1)).isFalse();
    }
}
