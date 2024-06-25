package com.retail.warehouse_management.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    @JsonIgnore
    private int productId;

    @Column(name = "product_name")
    @JsonProperty("name")
    private String productName;

//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    List<ProductArticleInfo> articles;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "products_articleInfo",
            joinColumns = @JoinColumn(name = "product_id")
    )
    @JsonProperty("contain_articles")
    private List<ProductArticle> articles;


    @Column(name = "product_price")
    @JsonProperty("price")
    private double productPrice;

    @Transient
    private Boolean isAvailable;

    @Transient
    private int quantityInStock;

    public Product() {
    }

    public Product(String productName, List<ProductArticle> articles, double productPrice) {
        this.productName = productName;
        this.articles = articles;
        this.productPrice = productPrice;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public List<ProductArticle> getArticles() {
        return articles;
    }

    public void setArticles(List<ProductArticle> articles) {
        this.articles = articles;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return productId == product.productId && Double.compare(productPrice, product.productPrice) == 0 && quantityInStock == product.quantityInStock && Objects.equals(productName, product.productName) && Objects.equals(articles, product.articles) && Objects.equals(isAvailable, product.isAvailable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, productName, articles, productPrice, isAvailable, quantityInStock);
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", articles=" + articles +
                ", productPrice=" + productPrice +
                ", isAvailable=" + isAvailable +
                ", quantityInStock=" + quantityInStock +
                '}';
    }
}
