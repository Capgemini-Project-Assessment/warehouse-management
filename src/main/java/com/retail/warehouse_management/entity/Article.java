package com.retail.warehouse_management.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "articles")
public class Article {
    @Id
    @Column(name = "art_id")
    @JsonProperty("art_id")
    private int articleId;

    @Column(name = "article_name")
    @JsonProperty("name")
    private String articleName;

    @Column(name = "article_stock")
    @JsonProperty("stock")
    private int articleStock;

    public Article() {
    }

    public Article(int articleId, String articleName, int articleStock) {
        this.articleId = articleId;
        this.articleName = articleName;
        this.articleStock = articleStock;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public int getArticleStock() {
        return articleStock;
    }

    public void setArticleStock(int articleStock) {
        this.articleStock = articleStock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article article)) return false;
        return articleId == article.articleId && articleStock == article.articleStock && Objects.equals(articleName, article.articleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(articleId, articleName, articleStock);
    }

    @Override
    public String toString() {
        return "Article{" +
                "articleId=" + articleId +
                ", articleName='" + articleName + '\'' +
                ", articleStock=" + articleStock +
                '}';
    }
}
