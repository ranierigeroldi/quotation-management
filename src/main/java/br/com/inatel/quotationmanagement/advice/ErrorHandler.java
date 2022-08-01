package br.com.inatel.quotationmanagement.advice;

import br.com.inatel.quotationmanagement.exception.QuotesNotFoundException;
import br.com.inatel.quotationmanagement.exception.StockNotFoundException;
import br.com.inatel.quotationmanagement.model.rest.AppError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.format.DateTimeParseException;

@ControllerAdvice
@ResponseBody
public class ErrorHandler {

    @ExceptionHandler(value={StockNotFoundException.class})
    @ResponseStatus(value= HttpStatus.BAD_REQUEST)
    AppError stockNotFoundException(StockNotFoundException exception) {
        return new AppError(exception.getMessage());
    }

    @ExceptionHandler(value={QuotesNotFoundException.class})
    @ResponseStatus(value= HttpStatus.BAD_REQUEST)
    AppError quotesNotFoundException(QuotesNotFoundException exception) {
        return new AppError(exception.getMessage());
    }

    @ExceptionHandler(value={DateTimeParseException.class})
    @ResponseStatus(value= HttpStatus.BAD_REQUEST)
    AppError dateTimeParseException(DateTimeParseException exception) {
        return new AppError("Invalid date: " + exception.getMessage());
    }

    @ExceptionHandler(value={NumberFormatException.class})
    @ResponseStatus(value= HttpStatus.BAD_REQUEST)
    AppError numberFormatException(NumberFormatException exception) {
        return new AppError("Invalid number: " + exception.getMessage());
    }

}
