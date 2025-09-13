package br.com.dio.repository;

import br.com.dio.entity.StockItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<StockItem, Long> {
    Optional<StockItem> findByProductId(String productId);
    boolean existsByProductId(String productId);
}