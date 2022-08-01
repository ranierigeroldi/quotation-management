package br.com.inatel.quotationmanagement.service;

import br.com.inatel.quotationmanagement.exception.QuotesNotFoundException;
import br.com.inatel.quotationmanagement.exception.StockNotFoundException;
import br.com.inatel.quotationmanagement.mapper.Mapper;
import br.com.inatel.quotationmanagement.model.dto.StockOperationDTO;
import br.com.inatel.quotationmanagement.repository.StockOperationRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockOperationService {

    private final StockOperationRepository stockOperationRepository;
    private final StockManagerCacheService stockManagerCacheService;
    private final Mapper mapper;

    StockOperationService(StockOperationRepository stockOperationRepository, StockManagerCacheService stockManagerCacheService, Mapper mapper) {
        this.stockOperationRepository = stockOperationRepository;
        this.stockManagerCacheService = stockManagerCacheService;
        this.mapper = mapper;
    }

    @Transactional
    public StockOperationDTO create(StockOperationDTO dto) {
        if (!dto.getQuotes().isPresent() || dto.getQuotes().get().isEmpty())
            throw new QuotesNotFoundException(dto);

        // if the reported stockId does not exist on cache returns an error
        if (!stockManagerCacheService.loadCache().contains(dto.getStockId()))
            throw new StockNotFoundException(dto);

        var stock = mapper.toStockOperation(dto);

        return mapper.toDto(stockOperationRepository.save(stock));
    }

    public List<StockOperationDTO> findAll() {
        return stockOperationRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public List<StockOperationDTO> findByStockId(String stockId) {
        return stockOperationRepository.findByStockId(stockId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

}
