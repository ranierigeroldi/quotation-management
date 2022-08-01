package br.com.inatel.quotationmanagement.model.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Entity
public class StockOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String stockId;

    @OneToMany(mappedBy = "stockOperation", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<StockQuote> quotes;

    public StockOperation() {}

    public StockOperation(UUID id, String stockId) {
        this(id, stockId, null);
    }

    public StockOperation(UUID id, String stockId, List<StockQuote> quotes) {
        this.id = id;
        this.stockId = stockId;
        this.quotes = quotes;
    }

    public Optional<UUID> getId() {
        return Optional.ofNullable(id);
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public Optional<List<StockQuote>> getQuotes() {
        return Optional.ofNullable(quotes);
    }

    public void setQuotes(List<StockQuote> quotes) {
        this.quotes = quotes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof StockOperation)) return false;

        StockOperation stock = (StockOperation) o;

        return Objects.equals(this.id, stock.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

}
