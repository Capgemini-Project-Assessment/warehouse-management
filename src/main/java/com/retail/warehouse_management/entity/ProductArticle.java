package com.retail.warehouse_management.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class ProductArticle {
    @JsonProperty("art_id")
    private int articleId;
    @JsonProperty("amount_of")
    private int articleAmount;
    @JsonIgnore
    private int articleInventoryStock;

    public ProductArticle() {
    }

    public ProductArticle(int articleId, int articleAmount) {
        this.articleId = articleId;
        this.articleAmount = articleAmount;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public int getArticleAmount() {
        return articleAmount;
    }

    public void setArticleAmount(int articleAmount) {
        this.articleAmount = articleAmount;
    }

    public int getArticleInventoryStock() {
        return articleInventoryStock;
    }

    public void setArticleInventoryStock(int articleInventoryStock) {
        this.articleInventoryStock = articleInventoryStock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductArticle that)) return false;
        return articleId == that.articleId && articleAmount == that.articleAmount && articleInventoryStock == that.articleInventoryStock;
    }

    @Override
    public int hashCode() {
        return Objects.hash(articleId, articleAmount, articleInventoryStock);
    }

    @Override
    public String toString() {
        return "ProductArticle{" +
                "articleId=" + articleId +
                ", articleAmount=" + articleAmount +
                ", remainingArticleInStock=" + articleInventoryStock +
                '}';
    }
}
