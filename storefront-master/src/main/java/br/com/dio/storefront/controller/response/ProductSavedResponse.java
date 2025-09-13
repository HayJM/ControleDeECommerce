package br.com.dio.storefront.controller.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductSavedResponse {
    private String id;
    private String name;
    private BigDecimal price;
    private LocalDateTime createdAt;
    private String message;

    // Constructors
    public ProductSavedResponse() {}

    public ProductSavedResponse(String id, String name, BigDecimal price, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.createdAt = createdAt;
        this.message = "Product created successfully";
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
