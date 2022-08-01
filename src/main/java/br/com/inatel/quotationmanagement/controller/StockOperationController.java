package br.com.inatel.quotationmanagement.controller;

import br.com.inatel.quotationmanagement.mapper.Mapper;
import br.com.inatel.quotationmanagement.model.dto.StockOperationDTO;
import br.com.inatel.quotationmanagement.service.StockOperationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
class StockOperationController {

    private final StockOperationService stockService;

    StockOperationController(StockOperationService stockService, Mapper mapper) {
        this.stockService = stockService;
    }

    @ApiOperation(value = "Create new quotes for the reported stock.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Quotes created successfully.", response = StockOperationDTO.class),
            @ApiResponse(code = 400, message = "Stock/Quotes not found error."),
            @ApiResponse(code = 500, message = "Please contact technical support."),
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(value = "/stock/operation", produces = "application/json")
    ResponseEntity<StockOperationDTO> create(@RequestBody StockOperationDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(stockService.create(dto));
    }

    @ApiOperation(value = "List all quotes from all stocks.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful retrieval."),
            @ApiResponse(code = 500, message = "Please contact technical support."),
    })
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/stock/operation", produces = "application/json")
    ResponseEntity<List<StockOperationDTO>> findAll() {
        return ResponseEntity.ok(stockService.findAll());
    }

    @ApiOperation(value = "List all quotes from the reported stock.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful retrieval."),
            @ApiResponse(code = 404, message = "Not Found."),
            @ApiResponse(code = 500, message = "Please contact technical support."),
    })
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/stock/operation/{stockId}", produces = "application/json")
    ResponseEntity<List<StockOperationDTO>> findByStockId(@PathVariable String stockId) {
        var entities = stockService.findByStockId(stockId);

        if (entities.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(entities);
    }

}