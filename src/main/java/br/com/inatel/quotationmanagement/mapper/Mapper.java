package br.com.inatel.quotationmanagement.mapper;

import br.com.inatel.quotationmanagement.model.dto.StockOperationDTO;
import br.com.inatel.quotationmanagement.model.entity.StockOperation;
import br.com.inatel.quotationmanagement.model.entity.StockQuote;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class Mapper {

    public StockOperationDTO toDto(StockOperation entity) {
        var id = entity.getId().isPresent() ? entity.getId().get().toString() : null;
        var quotes =
                entity.getQuotes()
                        .orElse(Collections.emptyList())
                        .stream()
                        .collect(Collectors.toMap(quote -> quote.getDate().toString(),
                                                  quote -> quote.getPrice().toString()));

        return new StockOperationDTO(id, entity.getStockId(), quotes);
    }

    public StockOperation toStockOperation(StockOperationDTO dto) {
        var id = dto.getId().isPresent() ? UUID.fromString(dto.getId().get()) : null;
        var entity = new StockOperation(id, dto.getStockId());

        entity.setQuotes(
                dto.getQuotes()
                    .orElse(Collections.emptyMap())
                    .entrySet()
                    .stream()
                    .map(quote ->
                        new StockQuote(LocalDate.parse(quote.getKey()), new BigDecimal(quote.getValue()), entity))
                    .collect(Collectors.toList()));

        return entity;
    }

}
