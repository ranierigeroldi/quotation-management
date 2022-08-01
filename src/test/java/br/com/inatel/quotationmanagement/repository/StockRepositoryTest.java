package br.com.inatel.quotationmanagement.repository;

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
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doAnswer;

@SpringBootTest
@ActiveProfiles("test")
public class StockRepositoryTest {

    @Autowired
    private StockOperationRepository stockRepository;
    @MockBean
    private StockManagerCacheService stockManagerCacheService;

    @BeforeEach
    public void mock_beans() {
        doAnswer(invocationOnMock -> null).when(stockManagerCacheService).register();
    }

    @Test
    public void should_generate_data() {
        var random = new Random();

        for (int count = 0; count < 1000; count++) {
            var entity = new StockOperation(UUID.randomUUID(), "test" + count, new ArrayList<>());
            var date = LocalDate.now();

            for (int quoteCount = 0; quoteCount < random.nextInt(1, 50); quoteCount++) {
                entity.getQuotes().get().add(new StockQuote(date.plusDays(quoteCount), new BigDecimal(quoteCount), entity));
            }

            stockRepository.save(entity);
        }

        assertEquals(1000, stockRepository.findAll().size());
    }

}
