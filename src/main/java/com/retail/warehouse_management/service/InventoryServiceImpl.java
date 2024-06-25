package com.retail.warehouse_management.service;

import com.retail.warehouse_management.dto.ArticleDto;
import com.retail.warehouse_management.dto.ProductInventory;
import com.retail.warehouse_management.entity.Article;
import com.retail.warehouse_management.exceptions.ArticleNotFoundException;
import com.retail.warehouse_management.exceptions.ErrorResponse;
import com.retail.warehouse_management.repository.InventoryRepository;
import com.retail.warehouse_management.utility.FileLoad;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;

@Service
public class InventoryServiceImpl implements InventoryService {
    @Value("${warehouse.inventory.file}")
    private String inventoryFile;
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private Map<Integer, ArticleDto> inventory = new HashMap<>();

    private final FileLoad fileLoad;
    private final InventoryRepository inventoryRepository;

    public InventoryServiceImpl(FileLoad fileLoad, InventoryRepository inventoryRepository) {
        this.fileLoad = fileLoad;
        this.inventoryRepository = inventoryRepository;
    }

    @PostConstruct
    public void init() {
        log.info("Loading inventory articles...");
        ProductInventory productInventory = fileLoad.loadInventoryArticlesFromFile(inventoryFile);
        inventory = productInventory.articleDtos().stream().collect(Collectors.toMap(ArticleDto::getArticleId, a -> a));
        if (!inventory.isEmpty()) {
            inventoryRepository.saveAll(mapArticleDtoToEntity(inventory.values()));
            log.info("Loaded {} inventory articles", inventory.size());
        } else {
            log.warn("No inventory loaded !");
        }
    }

    public List<Article> mapArticleDtoToEntity(Collection<ArticleDto> articleDtos) {
        ModelMapper mapper = new ModelMapper();
        return articleDtos.stream().map(article -> mapper.map(article, Article.class)).collect(Collectors.toList());
    }

    /**
     * Get article details from articleId
     *
     * @param articleId
     * @return Article
     */
    @Override
    public Article getArticleById(int articleId) {
        Optional<Article> article = inventoryRepository.findById(articleId);
        if(article.isPresent()){
            return article.get();
        } else{
            throw new ArticleNotFoundException(new ErrorResponse(String.format("Article with id %d is not found in inventory", articleId), HttpStatus.NOT_FOUND));
        }
    }

    /**
     * Create or Update Article
     *
     * @param article
     * @return Article
     */
    @Override
    public Article createOrUpdateArticle(Article article) {
        return inventoryRepository.save(article);
    }

    /**
     * Get stock of article from articleID
     *
     * @param articleId
     * @return stock of article
     */
    @Override
    public int getArticleStock(int articleId) {
        return getArticleById(articleId).getArticleStock();
    }

    /**
     * Update article stock after purchase
     *
     * @param articleId
     * @param quanityOfPurchase
     */
    @Override
    public Article updateArticleStock(int articleId, Integer quanityOfPurchase) {
        Article article = getArticleById(articleId);
        int remainingStock = article.getArticleStock() - quanityOfPurchase;
        article.setArticleStock(remainingStock);
        return createOrUpdateArticle(article);
    }

    /**
     *
     * @return List of all the articles from the inventory
     */
    public List<Article> getAllArticles() throws ArticleNotFoundException {
        List<Article> articles = inventoryRepository.findAll();
        if(articles.isEmpty()){
            throw new ArticleNotFoundException(new ErrorResponse("No article is present in inventory", HttpStatus.NOT_FOUND));
        }
        return articles;
    }

}
