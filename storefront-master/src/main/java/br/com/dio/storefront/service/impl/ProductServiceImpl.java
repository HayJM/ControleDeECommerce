package br.com.dio.storefront.service.impl;

import br.com.dio.storefront.dto.ProductDetailDTO;
import br.com.dio.storefront.dto.ProductInfoDTO;
import br.com.dio.storefront.dto.StockStatusMessage;
import br.com.dio.storefront.entity.ProductEntity;
import br.com.dio.storefront.exception.ProductNotFoundException;
import br.com.dio.storefront.mapper.IProductMapper;
import br.com.dio.storefront.repository.ProductRepository;
import br.com.dio.storefront.service.IProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final ProductRepository repository;
    private final RestClient warehouseClient;
    private final IProductMapper mapper;

    @Override
    public ProductEntity save(final ProductEntity entity) {
        return repository.save(entity);
    }

    @Override
    public void changeActivated(final UUID id, final boolean active) {
        var entity = findById(id);
        entity.setActive(active);
        repository.save(entity);
    }

    @Override
    public List<ProductEntity> findAllActive() {
        return repository.findByActiveTrueOrderByNameAsc();
    }

    @Override
    public ProductInfoDTO findInfo(final UUID id) {
        var entity = findById(id);
        var price = requestCurrentAmount(id);
        return mapper.toDTO(entity, price);
    }

    @Override
    public void purchase(final UUID id) {
        purchaseWarehouse(id);
    }

    private ProductEntity findById(final UUID id){
        return repository.findById(id).orElseThrow();
    }

    private BigDecimal requestCurrentAmount(final UUID id) {
        var dto = warehouseClient.get()
                .uri("/products/" + id)
                .retrieve()
                .body(ProductDetailDTO.class);
        return dto.price();
    }

    private void purchaseWarehouse(final UUID id){
        var path = String.format("/products/%s/purchase", id);
        warehouseClient.post()
                .uri(path)
                .retrieve()
                .toBodilessEntity();
    }

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private IProductMapper productMapper;

    @Override
    @Cacheable(value = "products", key = "#id")
    public Optional<ProductDetailDTO> findById(String id) {
        Optional<ProductEntity> productEntityOptional = productRepository.findById(id);

        if (productEntityOptional.isEmpty()) {
            return Optional.empty();
        }

        ProductEntity productEntity = productEntityOptional.get();
        return Optional.of(productMapper.toProductDetailDTO(productEntity));
    }

    @Override
    @Cacheable(value = "products")
    public List<ProductDetailDTO> findAll() {
        List<ProductEntity> productEntities = productRepository.findAll();
        return productMapper.toProductDetailDTO(productEntities);
    }

    @Override
    @CachePut(value = "products", key = "#result.id")
    public ProductDetailDTO save(ProductInfoDTO productInfo) {
        ProductEntity productEntity = productMapper.toProductEntity(productInfo);
        productEntity.setCreatedAt(LocalDateTime.now());
        productEntity.setUpdatedAt(LocalDateTime.now());

        ProductEntity savedEntity = productRepository.save(productEntity);
        return productMapper.toProductDetailDTO(savedEntity);
    }

    @Override
    @CacheEvict(value = "products", key = "#id")
    public void delete(String id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    @Override
    @CachePut(value = "products", key = "#id")
    public ProductDetailDTO changeAvailability(String id, StockStatusMessage stockStatusMessage) {
        Optional<ProductEntity> productEntityOptional = productRepository.findById(id);

        if (productEntityOptional.isEmpty()) {
            throw new ProductNotFoundException("Product not found with id: " + id);
        }

        ProductEntity productEntity = productEntityOptional.get();
        productEntity.setActive((Boolean) stockStatusMessage.getIsAvailable());
        productEntity.setUpdatedAt(LocalDateTime.now());

        ProductEntity updatedEntity = productRepository.save(productEntity);
        return productMapper.toProductDetailDTO(updatedEntity);
    }
}
