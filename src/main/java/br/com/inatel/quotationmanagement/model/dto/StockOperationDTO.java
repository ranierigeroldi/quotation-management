package br.com.inatel.quotationmanagement.model.dto;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class StockOperationDTO {

    private String id;
    private String stockId;
    private Map<String, String> quotes;

    public StockOperationDTO() {}

    public StockOperationDTO(String id, String stockId) {
        this(id, stockId, null);
    }

    public StockOperationDTO(String id, String stockId, Map<String, String> quotes) {
        this.id = id;
        this.stockId = stockId;
        this.quotes = quotes;
    }

    public Optional<String> getId() {
        return Optional.ofNullable(id);
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public Optional<Map<String, String>> getQuotes() {
        return Optional.ofNullable(quotes);
    }

    public void setQuotes(Map<String, String> quotes) {
        this.quotes = quotes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof StockOperationDTO)) return false;

        StockOperationDTO stockDTO = (StockOperationDTO) o;

        return Objects.equals(this.id, stockDTO.id)
                && Objects.equals(this.stockId, stockDTO.stockId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.stockId);
    }
}
