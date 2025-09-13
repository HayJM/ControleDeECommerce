package br.com.dio.service.impl;

import br.com.dio.entity.StockItem;
import br.com.dio.repository.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StockServiceImplTest {

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private StockServiceImpl stockService;

    private StockItem stockItem;

    @BeforeEach
    void setUp() {
        stockItem = new StockItem("PROD001", 10, "A1-B2");
        stockItem.setId(1L);
    }

    @Test
    void getAllStock_ShouldReturnAllStockItems() {
        List<StockItem> stockItems = Arrays.asList(stockItem);
        when(stockRepository.findAll()).thenReturn(stockItems);

        List<StockItem> result = stockService.getAllStock();

        assertEquals(1, result.size());
        assertEquals("PROD001", result.get(0).getProductId());
        verify(stockRepository).findAll();
    }

    @Test
    void getStockByProductId_ShouldReturnStockItem() {
        when(stockRepository.findByProductId("PROD001")).thenReturn(Optional.of(stockItem));

        Optional<StockItem> result = stockService.getStockByProductId("PROD001");

        assertTrue(result.isPresent());
        assertEquals("PROD001", result.get().getProductId());
        verify(stockRepository).findByProductId("PROD001");
    }

    @Test
    void createStock_ShouldSaveAndReturnStockItem() {
        when(stockRepository.save(any(StockItem.class))).thenReturn(stockItem);

        StockItem result = stockService.createStock(stockItem);

        assertEquals("PROD001", result.getProductId());
        assertEquals(10, result.getQuantity());
        verify(stockRepository).save(stockItem);
    }

    @Test
    void updateStock_ExistingProduct_ShouldUpdateQuantity() {
        when(stockRepository.findByProductId("PROD001")).thenReturn(Optional.of(stockItem));
        when(stockRepository.save(any(StockItem.class))).thenReturn(stockItem);

        Optional<StockItem> result = stockService.updateStock("PROD001", 20);

        assertTrue(result.isPresent());
        assertEquals(20, stockItem.getQuantity());
        verify(stockRepository).findByProductId("PROD001");
        verify(stockRepository).save(stockItem);
    }

    @Test
    void updateStock_NonExistingProduct_ShouldReturnEmpty() {
        when(stockRepository.findByProductId("NONEXISTENT")).thenReturn(Optional.empty());

        Optional<StockItem> result = stockService.updateStock("NONEXISTENT", 20);

        assertFalse(result.isPresent());
        verify(stockRepository).findByProductId("NONEXISTENT");
        verify(stockRepository, never()).save(any());
    }

    @Test
    void isAvailable_SufficientStock_ShouldReturnTrue() {
        when(stockRepository.findByProductId("PROD001")).thenReturn(Optional.of(stockItem));

        boolean result = stockService.isAvailable("PROD001", 5);

        assertTrue(result);
        verify(stockRepository).findByProductId("PROD001");
    }

    @Test
    void isAvailable_InsufficientStock_ShouldReturnFalse() {
        when(stockRepository.findByProductId("PROD001")).thenReturn(Optional.of(stockItem));

        boolean result = stockService.isAvailable("PROD001", 15);

        assertFalse(result);
        verify(stockRepository).findByProductId("PROD001");
    }
}