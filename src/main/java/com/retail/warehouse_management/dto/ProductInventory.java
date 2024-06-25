package com.retail.warehouse_management.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public record ProductInventory(
        List<ProductDto> products,

        @JsonProperty("inventory")
        List<ArticleDto> articleDtos) {
}
