package br.com.dio.storefront.dto;

import java.util.UUID;

public record StockStatusMessage(UUID id, String status) {

    public boolean active(){
        return status.equals("AVAILABLE");
    }

    public Object getIsAvailable() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getIsAvailable'");
    }

}