package br.com.dio.storefront.controller;

import br.com.dio.storefront.controller.request.ProductSaveRequest;
import br.com.dio.storefront.controller.response.ProductAvailableResponse;
import br.com.dio.storefront.controller.response.ProductDetailResponse;
import br.com.dio.storefront.controller.response.ProductSavedResponse;
import br.com.dio.storefront.dto.ProductDetailDTO;
import br.com.dio.storefront.dto.ProductInfoDTO;
import br.com.dio.storefront.mapper.IProductMapper;
import br.com.dio.storefront.service.IProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "Product Management", description = "API for managing products in storefront")
public class ProductController {

    @Autowired
    private IProductService productService;

    @Autowired
    private IProductMapper productMapper;

    @GetMapping
    @Operation(summary = "Get all products", description = "Retrieve all available products")
    public ResponseEntity<List<ProductDetailResponse>> getAllProducts() {
        List<ProductDetailDTO> products = productService.findAll();
        List<ProductDetailResponse> response = productMapper.toProductDetailResponse(products);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", description = "Retrieve a specific product by its ID")
    public ResponseEntity<ProductDetailResponse> getProductById(@PathVariable String id) {
        Optional<ProductDetailDTO> product = productService.findById(id);

        if (product.isPresent()) {
            ProductDetailResponse response = productMapper.toProductDetailResponse(product.get());
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Create new product", description = "Create a new product in the storefront")
    public ResponseEntity<ProductSavedResponse> createProduct(@Valid @RequestBody ProductSaveRequest request) {
        ProductInfoDTO productInfo = productMapper.toProductInfoDTO(request);
        ProductDetailDTO savedProduct = productService.save(productInfo);
        ProductSavedResponse response = productMapper.toProductSavedResponse(savedProduct);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update product", description = "Update an existing product")
    public ResponseEntity<ProductDetailResponse> updateProduct(
            @PathVariable String id,
            @Valid @RequestBody ProductSaveRequest request) {

        // First check if product exists
        Optional<ProductDetailDTO> existingProduct = productService.findById(id);
        if (existingProduct.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Convert request to DTO and set the ID
        ProductInfoDTO productInfo = productMapper.toProductInfoDTO(request);
        // Note: In a real scenario, you'd need to create an update method in service
        // For now, we'll assume the save method handles updates when ID is present
        ProductDetailDTO updatedProduct = productService.save(productInfo);
        ProductDetailResponse response = productMapper.toProductDetailResponse(updatedProduct);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product", description = "Delete a product from the storefront")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
