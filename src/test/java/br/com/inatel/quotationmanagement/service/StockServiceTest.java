package br.com.inatel.quotationmanagement.service;

import br.com.inatel.quotationmanagement.exception.QuotesNotFoundException;
import br.com.inatel.quotationmanagement.exception.StockNotFoundException;
import br.com.inatel.quotationmanagement.model.dto.StockOperationDTO;
import br.com.inatel.quotationmanagement.model.entity.StockOperation;
import br.com.inatel.quotationmanagement.model.entity.StockQuote;
import br.com.inatel.quotationmanagement.repository.StockOperationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class StockServiceTest {

    @MockBean
    private StockManagerCacheService stockManagerCacheService;
    @MockBean
    private StockOperationRepository stockRepository;
    @Autowired
    private StockOperationService stockService;
    private List<StockQuote> quotes;
    private StockOperation operation;
    private List<StockOperation> operations;

    @BeforeEach
    public void mock_beans() {
        doAnswer(invocationOnMock -> null).when(stockManagerCacheService).register();
        when(stockManagerCacheService.loadCache()).thenReturn(List.of("petr4", "aapl34"));

        quotes = List.of(new StockQuote(LocalDate.now(), BigDecimal.ZERO));
        operation = new StockOperation(UUID.fromString("558f5f50-34fa-4c99-8022-b86205cc6e3a"), "aapl34", quotes);
        operations = List.of(operation);
    }

    @Test
    public void when_create_it_should_return_id() {
        when(stockRepository.save(any(StockOperation.class))).thenReturn(operation);

        final var dto = new StockOperationDTO(null, "aapl34", new HashMap<>());
        dto.getQuotes().get().put(LocalDate.now().toString(), "0");

        final var created = stockService.create(dto);

        assertEquals("558f5f50-34fa-4c99-8022-b86205cc6e3a", created.getId().get());
    }

    @Test
    public void when_create_with_wrong_stock_id_it_should_throw_exception() {
        final var dto = new StockOperationDTO(null, "petr41", new HashMap<>());
        dto.getQuotes().get().put(LocalDate.now().toString(), "0");

        assertThrows(StockNotFoundException.class, () ->  stockService.create(dto));
    }

    @Test
    public void when_create_without_quotes_it_should_throw_exception() {
        final var dto = new StockOperationDTO(null, "petr41", new HashMap<>());

        assertThrows(QuotesNotFoundException.class, () ->  stockService.create(dto));
    }

    @Test
    public void when_find_all_it_should_return_values() {
        when(stockRepository.findAll()).thenReturn(operations);

        final var operations = stockService.findAll();

        assertEquals(1, operations.size());
    }

    @Test
    public void when_find_by_stock_id_it_should_not_return_values() {
        final var operations = stockService.findByStockId("petr41");

        assertEquals(0, operations.size());
    }

}
