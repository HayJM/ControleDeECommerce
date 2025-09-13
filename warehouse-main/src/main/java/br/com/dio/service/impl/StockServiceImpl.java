package br.com.dio.service.impl;

import br.com.dio.entity.StockItem;
import br.com.dio.repository.StockRepository;
import br.com.dio.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockRepository stockRepository;

    @Override
    public List<StockItem> getAllStock() {
        return stockRepository.findAll();
    }

    @Override
    public Optional<StockItem> getStockByProductId(String productId) {
        return stockRepository.findByProductId(productId);
    }

    @Override
    public StockItem createStock(StockItem stockItem) {
        return stockRepository.save(stockItem);
    }

    @Override
    public Optional<StockItem> updateStock(String productId, Integer quantity) {
        Optional<StockItem> stockOptional = stockRepository.findByProductId(productId);
        if (stockOptional.isPresent()) {
            StockItem stock = stockOptional.get();
            stock.setQuantity(quantity);
            return Optional.of(stockRepository.save(stock));
        }
        return Optional.empty();
    }

    @Override
    public void deleteStock(String productId) {
        stockRepository.findByProductId(productId)
                .ifPresent(stock -> stockRepository.delete(stock));
    }

    @Override
    public boolean isAvailable(String productId, Integer quantity) {
        Optional<StockItem> stockOptional = stockRepository.findByProductId(productId);
        return stockOptional.map(stock -> stock.getQuantity() >= quantity).orElse(false);
    }
}