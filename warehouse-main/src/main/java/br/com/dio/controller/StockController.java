package br.com.dio.controller;

import br.com.dio.entity.StockItem;
import br.com.dio.service.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stock")
@Tag(name = "Stock Management", description = "API for managing warehouse stock")
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping
    @Operation(summary = "Get all stock items")
    public ResponseEntity<List<StockItem>> getAllStock() {
        return ResponseEntity.ok(stockService.getAllStock());
    }

    @GetMapping("/{productId}")
    @Operation(summary = "Get stock by product ID")
    public ResponseEntity<StockItem> getStockByProductId(@PathVariable String productId) {
        return stockService.getStockByProductId(productId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create new stock item")
    public ResponseEntity<StockItem> createStock(@Valid @RequestBody StockItem stockItem) {
        return ResponseEntity.ok(stockService.createStock(stockItem));
    }

    @PutMapping("/{productId}")
    @Operation(summary = "Update stock quantity")
    public ResponseEntity<StockItem> updateStock(@PathVariable String productId, 
                                                @RequestParam Integer quantity) {
        return stockService.updateStock(productId, quantity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{productId}")
    @Operation(summary = "Delete stock item")
    public ResponseEntity<Void> deleteStock(@PathVariable String productId) {
        stockService.deleteStock(productId);
        return ResponseEntity.noContent().build();
    }
}