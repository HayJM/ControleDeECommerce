package br.com.dio.storefront.controller.response;

public class ProductAvailableResponse {
    private String productId;
    private Boolean available;
    private String message;

    // Constructors
    public ProductAvailableResponse() {}

    public ProductAvailableResponse(String productId, Boolean available, String message) {
        this.productId = productId;
        this.available = available;
        this.message = message;
    }

    // Getters and setters
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public Boolean getAvailable() { return available; }
    public void setAvailable(Boolean available) { this.available = available; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
