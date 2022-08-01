package br.com.inatel.quotationmanagement.controller;

import br.com.inatel.quotationmanagement.exception.StockNotFoundException;
import br.com.inatel.quotationmanagement.model.dto.StockOperationDTO;
import br.com.inatel.quotationmanagement.service.StockManagerCacheService;
import br.com.inatel.quotationmanagement.service.StockOperationService;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class StockControllerTest {

    @MockBean
    private StockManagerCacheService stockManagerCacheService;
    @MockBean
    private StockOperationService stockService;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void mock_initialization() {
        doAnswer(invocationOnMock -> null).when(stockManagerCacheService).register();
    }

    @Test
    public void when_create_it_should_return_created() throws Exception {
        final var dto = new StockOperationDTO(null, "aapl34", new HashMap<>());
        dto.getQuotes().get().put("2022-07-31", "0");

        final var dtoCreated = new StockOperationDTO("421e7b10-fe94-4bda-ac53-2c32bc5725fc", "aapl34", new HashMap<>());
        dtoCreated.getQuotes().get().put("2022-07-31", "0");

        when(stockService.create(dto)).thenReturn(dtoCreated);

        final var quotes = new JSONObject();
        quotes.put(LocalDate.now().toString(), "0");

        final var stock = new JSONObject();
        stock.put("stockId", "aapl34");
        stock.put("quotes", quotes);

        final var expectedJson = "{\"id\":\"421e7b10-fe94-4bda-ac53-2c32bc5725fc\",\"stockId\":\"aapl34\",\"quotes\":{\"2022-07-31\":\"0\"}}";

        mockMvc.perform(
                post("/stock/operation")
                        .content(stock.toString()).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedJson));
    }

    @Test
    public void when_create_it_should_return_bad_request() throws Exception {
        final var dto = new StockOperationDTO(null, "aapl34111");

        when(stockService.create(dto)).thenThrow(StockNotFoundException.class);

        final var stock = new JSONObject();
        stock.put("stockId", "aapl34111");

        mockMvc.perform(
                post("/stock/operation")
                        .content(stock.toString()).contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    public void when_find_all_it_should_return_values() throws Exception {
        final var operations = List.of(
                new StockOperationDTO("1", "aapl34111"),
                new StockOperationDTO("2", "test1"),
                new StockOperationDTO("3", "test2"));

        when(stockService.findAll()).thenReturn(operations);

        mockMvc.perform(get("/stock/operation"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{}, {}, {}]"));
    }

    @Test
    public void when_find_with_stock_id_it_should_return_not_found() throws Exception {
        when(stockService.findByStockId("3")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/stock/operation/3"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}
