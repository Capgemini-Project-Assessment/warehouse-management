package com.retail.warehouse_management.repository;

import com.retail.warehouse_management.entity.Article;
import com.retail.warehouse_management.exceptions.ArticleNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
public class InventoryRespositoryTest {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Test
    public void testGetAllArticles(){
        inventoryRepository.save(new Article(1,"ABC",12));

        List<Article> articleFromInventory = inventoryRepository.findAll();

        assert(!articleFromInventory.isEmpty());
        assertThat(articleFromInventory.size()).isEqualTo(1);
        assertThat(articleFromInventory.get(0).getArticleId()).isEqualTo(1);
        assertThat(articleFromInventory.get(0).getArticleName()).isEqualTo("ABC");
    }

    @Test
    public void testArticleById(){
        inventoryRepository.save(new Article(1,"ABC",12));

        Article articleFromInventory = inventoryRepository.findById(1).get();
        assertThat(articleFromInventory.getArticleName()).isEqualTo("ABC");
        assertThat(articleFromInventory.getArticleStock()).isEqualTo(12);
    }

}
