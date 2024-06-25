package com.retail.warehouse_management.service;

import com.retail.warehouse_management.entity.Article;
import com.retail.warehouse_management.exceptions.ArticleNotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface InventoryService {
    public Article getArticleById(int articleId);
    public Article createOrUpdateArticle(Article article);

    public int getArticleStock(int articleId) throws ArticleNotFoundException;
    public Article updateArticleStock(int articleId, Integer quanityOfPurchase);

    List<Article> getAllArticles() throws ArticleNotFoundException;
}
