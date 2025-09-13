package br.com.dio.storefront.repository;

import br.com.dio.storefront.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {

  List<ProductEntity> findByActiveTrueOrderByNameAsc();

  boolean existsById(String id);

  void deleteById(String id);

  Optional<ProductEntity> findById(String id);

}