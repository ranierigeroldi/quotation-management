package br.com.inatel.quotationmanagement.mapper;

import br.com.inatel.quotationmanagement.model.dto.StockOperationDTO;
import br.com.inatel.quotationmanagement.model.entity.StockOperation;
import br.com.inatel.quotationmanagement.model.entity.StockQuote;
import br.com.inatel.quotationmanagement.service.StockManagerCacheService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doAnswer;

@SpringBootTest
@ActiveProfiles("test")
public class MapperTest {

    @Autowired
    private Mapper mapper;
    @MockBean
    private StockManagerCacheService stockManagerCacheService;

    @BeforeEach
    public void mock_beans() {
        doAnswer(invocationOnMock -> null).when(stockManagerCacheService).register();
    }

    @Test
    public void should_convert_dto_to_stock_operation() {
        final var id = UUID.randomUUID();
        final var dto = new StockOperationDTO(id.toString(), "test1", new HashMap<>());
        dto.getQuotes().get().put("2022-08-01", "1");
        dto.getQuotes().get().put("2022-08-02", "2");
        dto.getQuotes().get().put("2022-08-03", "3");
        dto.getQuotes().get().put("2022-08-04", "4");

        final var entity = mapper.toStockOperation(dto);

        assertEquals(id, entity.getId().get());
    }

    @Test
    public void should_convert_dto_to_stock_operation_without_id() {
        final var dto = new StockOperationDTO(null, "test1", new HashMap<>());
        dto.getQuotes().get().put("2022-08-01", "1");
        dto.getQuotes().get().put("2022-08-02", "2");
        dto.getQuotes().get().put("2022-08-03", "3");
        dto.getQuotes().get().put("2022-08-04", "4");

        final var entity = mapper.toStockOperation(dto);

        assertFalse(entity.getId().isPresent());
    }

    @Test
    public void should_convert_dto_to_stock_operation_without_quotes() {
        final var dto = new StockOperationDTO(UUID.randomUUID().toString(), "test1");
        final var entity = mapper.toStockOperation(dto);

        assertTrue(entity.getQuotes().get().isEmpty());
    }

    @Test
    public void should_convert_stock_operation_to_dto() {
        final var id = UUID.randomUUID();
        final var entity = new StockOperation(id, "test1", new ArrayList<>());
        entity.getQuotes().get().add(new StockQuote(LocalDate.parse("2022-08-01"), new BigDecimal(1)));
        entity.getQuotes().get().add(new StockQuote(LocalDate.parse("2022-08-02"), new BigDecimal(2)));
        entity.getQuotes().get().add(new StockQuote(LocalDate.parse("2022-08-03"), new BigDecimal(3)));
        entity.getQuotes().get().add(new StockQuote(LocalDate.parse("2022-08-04"), new BigDecimal(4)));

        final var dto = mapper.toDto(entity);

        assertEquals(id.toString(), dto.getId().get());
    }

    @Test
    public void should_convert_stock_operation_to_dto_without_id() {
        final var entity = new StockOperation(null, "test1", new ArrayList<>());
        entity.getQuotes().get().add(new StockQuote(LocalDate.parse("2022-08-01"), new BigDecimal(1)));
        entity.getQuotes().get().add(new StockQuote(LocalDate.parse("2022-08-02"), new BigDecimal(2)));
        entity.getQuotes().get().add(new StockQuote(LocalDate.parse("2022-08-03"), new BigDecimal(3)));
        entity.getQuotes().get().add(new StockQuote(LocalDate.parse("2022-08-04"), new BigDecimal(4)));

        final var dto = mapper.toDto(entity);

        assertFalse(dto.getId().isPresent());
    }

    @Test
    public void should_convert_stock_operation_to_dto_without_quotes() {
        final var stock = new StockOperation(UUID.randomUUID(), "test1");
        final var dto = mapper.toDto(stock);

        assertTrue(dto.getQuotes().get().isEmpty());
    }

}
