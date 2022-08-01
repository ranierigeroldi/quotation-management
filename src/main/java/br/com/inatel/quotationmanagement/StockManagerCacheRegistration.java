package br.com.inatel.quotationmanagement;

import br.com.inatel.quotationmanagement.service.StockManagerCacheService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StockManagerCacheRegistration {

    @Bean
    CommandLineRunner register(StockManagerCacheService cacheService) {
        return args -> cacheService.register();
    }

}
