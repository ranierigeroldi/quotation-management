package br.com.inatel.quotationmanagement.exception;

import br.com.inatel.quotationmanagement.model.dto.StockOperationDTO;

public class StockNotFoundException extends RuntimeException {

    public StockNotFoundException(StockOperationDTO dto) {
        super(String.format("Stock with id='%s' not found.", dto.getStockId()));
    }

}
