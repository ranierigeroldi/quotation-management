package br.com.inatel.quotationmanagement.exception;

import br.com.inatel.quotationmanagement.model.dto.StockOperationDTO;

public class QuotesNotFoundException extends RuntimeException {

    public QuotesNotFoundException(StockOperationDTO dto) {
        super("Quotes are required for this operation.");
    }

}
