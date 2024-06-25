package com.retail.warehouse_management.repository;

import com.retail.warehouse_management.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Article, Integer> {
}
