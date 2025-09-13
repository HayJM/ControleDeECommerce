package br.com.dio.service;

import br.com.dio.entity.StockItem;

import java.util.List;
import java.util.Optional;

public interface StockService {
    List<StockItem> getAllStock();
    Optional<StockItem> getStockByProductId(String productId);
    StockItem createStock(StockItem stockItem);
    Optional<StockItem> updateStock(String productId, Integer quantity);
    void deleteStock(String productId);
    boolean isAvailable(String productId, Integer quantity);
}