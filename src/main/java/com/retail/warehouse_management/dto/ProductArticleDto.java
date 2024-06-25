package com.retail.warehouse_management.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Objects;

public class ProductArticleDto implements Serializable{
    @JsonProperty("art_id")
    int articleId;
    @JsonProperty("amount_of")
    int articleAmount;

    public ProductArticleDto() {
    }

    public ProductArticleDto(int articleId, int articleAmount) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductArticleDto that)) return false;
        return articleId == that.articleId && articleAmount == that.articleAmount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(articleId, articleAmount);
    }

    @Override
    public String toString() {
        return "ProductArticleDto{" +
                "articleId=" + articleId +
                ", articleAmount=" + articleAmount +
                '}';
    }
}
