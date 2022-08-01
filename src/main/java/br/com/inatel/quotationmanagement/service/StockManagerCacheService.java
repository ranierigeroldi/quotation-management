package br.com.inatel.quotationmanagement.service;

import br.com.inatel.quotationmanagement.model.dto.StockManagerDTO;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class StockManagerCacheService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockManagerCacheService.class);
    private static final HttpHeaders REQUEST_HEADERS;

    private final RestTemplate restTemplate;
    @Value("${stock.manager.url:http://localhost:8080}")
    private String stockManagerUrl;
    @Value("${registration.host:localhost}")
    private String registrationHost;

    StockManagerCacheService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    static {
        REQUEST_HEADERS = new HttpHeaders();
        REQUEST_HEADERS.setContentType(MediaType.APPLICATION_JSON);
    }

    /**
     * Clears the cache from stockManager using {@link CacheEvict}.
     */
    @CacheEvict(value = "stockManager", allEntries = true)
    public void clearCache() {
        LOGGER.info("stockManager cache cleared.");
    }

    /**
     * Caches the data from stock-manager.
     *
     * <p>The returned {@link List} contains only the stockId, which is needed for validation.
     */
    @Cacheable(value = "stockManager")
    public List<String> loadCache() {
        LOGGER.info("Querying stock-manager for cache.");

        final var stocks = restTemplate.getForObject(stockManagerUrl + "/stock", StockManagerDTO[].class);

        return Arrays.stream(stocks).map(StockManagerDTO::getId).toList();
    }

    /**
     * Tries to register with the stock-manager application on startup.
     * In case of failure, it ends the execution as we depend on the data provided by the stock-manager.
     */
    public void register() {
        final var request = new HttpEntity(registrationObject(), REQUEST_HEADERS);

        try {
            final var response = restTemplate.postForEntity(stockManagerUrl + "/notification", request, String.class);

            if (response.getStatusCode() != HttpStatus.OK)
                throw new RuntimeException("Could not notify stock-manager. Status: " + response.getStatusCode());
            else
                LOGGER.info("stock-manager successfully notified.");
        } catch (ResourceAccessException e) {
            e.printStackTrace();

            throw new RuntimeException("Could not notify stock-manager.");
        }
    }

    private String registrationObject() {
        final var notification = new JSONObject();
        notification.put("host", registrationHost);
        notification.put("port", 8081);

        return notification.toString();
    }

}
