package com.jarikkomarik.exchangeapi.exceptions;

import com.jarikkomarik.exchangeapi.dto.CurrencyRateResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
public class DataRetrievalException extends RuntimeException {
    private final CurrencyRateResponse currencyRateResponse;

    public DataRetrievalException (CurrencyRateResponse currencyRateResponse) {
        super(currencyRateResponse.getErrorMessage());
        this.currencyRateResponse = currencyRateResponse;
    }
}
