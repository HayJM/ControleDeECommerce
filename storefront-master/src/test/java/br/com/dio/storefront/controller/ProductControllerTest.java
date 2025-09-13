package br.com.dio.storefront.controller;

import br.com.dio.storefront.config.TestSecurityConfig;
import br.com.dio.storefront.dto.ProductDetailDTO;
import br.com.dio.storefront.dto.ProductInfoDTO;
import br.com.dio.storefront.mapper.IProductMapper;
import br.com.dio.storefront.service.IProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@Import(TestSecurityConfig.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IProductService productService;

    @MockBean
    private IProductMapper productMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllProducts_ShouldReturnProducts() throws Exception {
        ProductDetailDTO product = createTestProduct();
        when(productService.findAll()).thenReturn(Arrays.asList(product));

        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getProductById_ShouldReturnProduct() throws Exception {
        ProductDetailDTO product = createTestProduct();
        when(productService.findById("1")).thenReturn(Optional.of(product));

        mockMvc.perform(get("/api/v1/products/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getProductById_NotFound_ShouldReturn404() throws Exception {
        when(productService.findById("999")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/products/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createProduct_ShouldReturnCreatedProduct() throws Exception {
        ProductInfoDTO productInfo = createTestProductInfo();
        ProductDetailDTO savedProduct = createTestProduct();
        
        when(productMapper.toProductInfoDTO(any())).thenReturn(productInfo);
        when(productService.save(any(ProductInfoDTO.class))).thenReturn(savedProduct);

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productInfo)))
                .andExpect(status().isCreated());
    }

    private ProductDetailDTO createTestProduct() {
        return new ProductDetailDTO(
            UUID.fromString("550e8400-e29b-41d4-a716-446655440000"),
            "Test Product",
            BigDecimal.valueOf(99.99),
            "Test Description",
            "Test Category",
            true,
            LocalDateTime.now(),
            LocalDateTime.now()
        );
    }

    private ProductInfoDTO createTestProductInfo() {
        ProductInfoDTO productInfo = new ProductInfoDTO();
        productInfo.setName("New Product");
        productInfo.setDescription("Description");
        productInfo.setPrice(BigDecimal.valueOf(149.99));
        return productInfo;
    }
}