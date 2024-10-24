package com.jarikkomarik.exchangeapi.controller;

import com.jarikkomarik.exchangeapi.dto.CurrencyRateResponse;
import com.jarikkomarik.exchangeapi.exceptions.DataRetrievalException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.jarikkomarik.exchangeapi.Constants.FAILED_TO_GET_AMDOREN_RATES;
import static com.jarikkomarik.exchangeapi.Constants.NON_SUPPORTED_CURRENCY_PAIR;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(DataRetrievalException.class)
    public ResponseEntity<CurrencyRateResponse> handleDataRetrievalException(DataRetrievalException ex) {
        CurrencyRateResponse currencyRateResponse = ex.getCurrencyRateResponse();
        if (NON_SUPPORTED_CURRENCY_PAIR.equals(currencyRateResponse.getErrorMessage())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(currencyRateResponse);
        } else if (FAILED_TO_GET_AMDOREN_RATES.equals(currencyRateResponse.getErrorMessage())) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(currencyRateResponse);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(currencyRateResponse);
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An unexpected error occurred: " + ex.getMessage());
    }
}

