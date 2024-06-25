package com.retail.warehouse_management.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.retail.warehouse_management.utility.FileLoad;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;


public class ProductDto implements Serializable {
    @JsonProperty(value = "name", required = true)
    private String productName;
    @JsonProperty(value = "contain_articles", required = true)
    private List<ProductArticleDto> articles;
    @JsonProperty("price")
    private double productPrice;

    public ProductDto() {
    }

    public ProductDto(String productName, List<ProductArticleDto> articles, double productPrice) {
        this.productName = productName;
        this.articles = articles;
        this.productPrice = productPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public List<ProductArticleDto> getArticles() {
        return articles;
    }

    public void setArticles(List<ProductArticleDto> articles) {
        this.articles = articles;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductDto product)) return false;
        return Double.compare(productPrice, product.productPrice) == 0 && Objects.equals(productName, product.productName) && Objects.equals(articles, product.articles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, articles, productPrice);
    }

    @Override
    public String toString() {
        return "Product{" +
                "productName='" + productName + '\'' +
                ", articles=" + articles +
                ", productPrice=" + productPrice +
                '}';
    }
}
