package com.retail.warehouse_management.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

public class ArticleDto implements Serializable{
        @JsonProperty(value = "art_id", required = true)
        private int articleId;

        @JsonProperty(value = "name", required = true)
        private String articleName;

        @JsonProperty(value = "stock", required = true)
        private int articleStock;

        public ArticleDto() {
        }

        public ArticleDto(int articleId, String articleName, int articleStock) {
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
                if (!(o instanceof ArticleDto that)) return false;
            return articleId == that.articleId && articleStock == that.articleStock && Objects.equals(articleName, that.articleName);
        }

        @Override
        public int hashCode() {
                return Objects.hash(articleId, articleName, articleStock);
        }

        @Override
        public String toString() {
                return "ArticleDto{" +
                        "articleId=" + articleId +
                        ", articleName='" + articleName + '\'' +
                        ", articleStock=" + articleStock +
                        '}';
        }
}
