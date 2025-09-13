package br.com.dio.storefront.mapper;

import br.com.dio.storefront.controller.request.ProductSaveRequest;
import br.com.dio.storefront.controller.response.ProductDetailResponse;
import br.com.dio.storefront.controller.response.ProductSavedResponse;
import br.com.dio.storefront.dto.ProductDetailDTO;
import br.com.dio.storefront.dto.ProductInfoDTO;
import br.com.dio.storefront.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = "spring")
public interface IProductMapper {

    // Entity <-> DTO mappings
    @Mapping(target = "price", source = "price")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "available", source = "active")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    ProductDetailDTO toProductDetailDTO(ProductEntity productEntity);
    
    List<ProductDetailDTO> toProductDetailDTO(List<ProductEntity> productEntities);
    
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", constant = "true")
    ProductEntity toProductEntity(ProductInfoDTO productInfoDTO);

    // Request <-> DTO mappings
    ProductInfoDTO toProductInfoDTO(ProductSaveRequest request);

    // DTO <-> Response mappings
    @Mapping(target = "description", source = "description")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "available", source = "available")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    ProductDetailResponse toProductDetailResponse(ProductDetailDTO productDetailDTO);
    
    List<ProductDetailResponse> toProductDetailResponse(List<ProductDetailDTO> productDetailDTOs);
    
    @Mapping(target = "message", constant = "Product saved successfully")
    @Mapping(target = "createdAt", source = "createdAt")
    ProductSavedResponse toProductSavedResponse(ProductDetailDTO productDetailDTO);
    
    @Mapping(target = "description", source = "entity.description")
    @Mapping(target = "category", source = "entity.category")
    ProductInfoDTO toDTO(ProductEntity entity, BigDecimal price);
}
