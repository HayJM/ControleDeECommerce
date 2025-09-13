package br.com.dio.storefront.service;

import br.com.dio.storefront.dto.ProductDetailDTO;
import br.com.dio.storefront.dto.ProductInfoDTO;
import br.com.dio.storefront.dto.StockStatusMessage;
import br.com.dio.storefront.entity.ProductEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IProductService {

    ProductEntity save(final ProductEntity entity);

    void changeActivated(final UUID id, final boolean active);

    List<ProductEntity> findAllActive();

    ProductInfoDTO findInfo(final UUID id);

    void purchase(final UUID id);

    List<ProductDetailDTO> findAll();

    Optional<ProductDetailDTO> findById(String id);

    ProductDetailDTO save(ProductInfoDTO productInfo);

    void delete(String id);

    ProductDetailDTO changeAvailability(String id, StockStatusMessage stockStatusMessage);

}
