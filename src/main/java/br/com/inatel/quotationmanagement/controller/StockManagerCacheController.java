package br.com.inatel.quotationmanagement.controller;

import br.com.inatel.quotationmanagement.service.StockManagerCacheService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
class StockManagerCacheController {

    private final StockManagerCacheService stockManagerCacheService;

    StockManagerCacheController(StockManagerCacheService stockManagerCacheService) {
        this.stockManagerCacheService = stockManagerCacheService;
    }

    @ApiOperation(value = "Clears stock-manager cache.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful."),
            @ApiResponse(code = 500, message = "Please contact technical support."),
    })
    @ResponseStatus(value = HttpStatus.OK)
    @DeleteMapping(value = "/stockcache")
    ResponseEntity<Void> delete() {
        stockManagerCacheService.clearCache();

        return ResponseEntity.ok().build();
    }

}