package com.jarikkomarik.exchangeapi.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CurrencyRateResponse {
    private String firstCurrency;
    private String secondCurrency;
    private double cnbRate;
    private double amdorenRate;
    private double cnbToAmdorenDiff;
    String errorMessage;
}
