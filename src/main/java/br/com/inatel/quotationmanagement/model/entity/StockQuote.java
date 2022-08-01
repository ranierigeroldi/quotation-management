package br.com.inatel.quotationmanagement.model.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class StockQuote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private StockOperation stockOperation;
    private LocalDate date;
    private BigDecimal price;

    public StockQuote() {}

    public StockQuote(LocalDate date, BigDecimal price) {
        this(date, price, null);
    }

    public StockQuote(LocalDate date, BigDecimal price, StockOperation stockOperation) {
        this.date = date;
        this.price = price;
        this.stockOperation = stockOperation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StockOperation getStockOperation() {
        return stockOperation;
    }

    public void setStock(StockOperation stockOperation) {
        this.stockOperation = stockOperation;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof StockQuote)) return false;

        StockQuote quote = (StockQuote) o;

        return Objects.equals(this.id, quote.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

}
