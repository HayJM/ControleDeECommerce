package br.com.dio.storefront.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ProductDetailDTO(
        @JsonProperty("id")
        UUID id,
        @JsonProperty("name")
        String name,
        @JsonProperty("price")
        BigDecimal price,
        @JsonProperty("description")
        String description,
        @JsonProperty("category")
        String category,
        @JsonProperty("available")
        Boolean available,
        @JsonProperty("createdAt")
        LocalDateTime createdAt,
        @JsonProperty("updatedAt")
        LocalDateTime updatedAt) {
}
