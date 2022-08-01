package br.com.inatel.quotationmanagement;

import br.com.inatel.quotationmanagement.service.StockManagerCacheService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.doAnswer;

@SpringBootTest
@ActiveProfiles("test")
class QuotationManagementApplicationTests {

	@MockBean
	private StockManagerCacheService stockManagerCacheService;

	@BeforeEach
	public void mock_beans() {
		doAnswer(invocationOnMock -> null).when(stockManagerCacheService).register();
	}

	@Test
	void contextLoads() {
	}

}
