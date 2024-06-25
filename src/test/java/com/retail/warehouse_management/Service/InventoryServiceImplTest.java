package com.retail.warehouse_management.Service;

import com.retail.warehouse_management.dto.ArticleDto;
import com.retail.warehouse_management.entity.Article;
import com.retail.warehouse_management.exceptions.ArticleNotFoundException;
import com.retail.warehouse_management.repository.InventoryRepository;
import com.retail.warehouse_management.service.InventoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InventoryServiceImplTest {

    @InjectMocks
    InventoryServiceImpl inventoryService;

    @Mock
    InventoryRepository inventoryRepository;

    @Test
    public void testGetArticleById(){
        Article article = new Article(1, "A", 3);
        when(inventoryRepository.findById(1)).thenReturn(Optional.ofNullable(article));

        assertThat(inventoryService.getArticleById(1)).isNotNull();
        assertThat(inventoryService.getArticleById(1).getArticleName()).isEqualTo("A");
        assertThat(inventoryService.getArticleById(1).getArticleName()).isEqualTo("A");
    }

    @Test
    public void testGetAllArticles(){
        Article article1 = new Article(1, "A", 3);
        Article article2 = new Article(2, "B", 6);
        Article article3 = new Article(3, "C", 9);
        List<Article> articles = new ArrayList<>(List.of(article1,article2,article3));
        when(inventoryRepository.findAll()).thenReturn(articles);

        assertThat(inventoryService.getAllArticles()).isNotNull();
        assertThat(inventoryService.getAllArticles()).isNotEmpty();
        assertThat(inventoryService.getAllArticles().size()).isEqualTo(3);
    }

    @Test
    public void testArticleNotFoundException(){
        Article article = new Article(1, "A", 3);
        // Disable strict stubbing
        Mockito.lenient().when(inventoryRepository.findById(1)).thenReturn(Optional.ofNullable(article));
        ArticleNotFoundException exception = assertThrows(ArticleNotFoundException.class, () -> inventoryService.getArticleById(3));
        assertEquals("Article with id 3 is not found in inventory", exception.getErrorResponse().message());
    }

    @Test
    public void testcreateOrUpdateArticle(){
        Article article = new Article(1, "A", 3);
        when(inventoryRepository.save(article)).thenReturn(article);
        assertThat(inventoryService.createOrUpdateArticle(article)).isNotNull();
        assertThat(inventoryService.createOrUpdateArticle(article).getArticleStock()).isEqualTo(3);
    }

    @Test
    public void testGetArticleStock(){
        Article article = new Article(1, "A", 3);
        when(inventoryRepository.findById(1)).thenReturn(Optional.ofNullable(article));
        assertThat(inventoryService.getArticleStock(1)).isNotNull();
        assertThat(inventoryService.getArticleStock(1)).isEqualTo(3);
    }

    @Test
    public void testMapArticleDtoToEntity(){
        List<ArticleDto> articleDtoList = new ArrayList<>(List.of(new ArticleDto(1,"A",3),new ArticleDto(1,"A",3)));
        List<Article> articleList = inventoryService.mapArticleDtoToEntity(articleDtoList);
        assertThat(articleList).isNotEmpty();
        assertThat(articleList.size()).isEqualTo(2);
    }
}
