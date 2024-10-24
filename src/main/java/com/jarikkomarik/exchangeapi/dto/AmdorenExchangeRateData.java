package com.jarikkomarik.exchangeapi.dto;

import lombok.Data;

@Data
public class AmdorenExchangeRateData {

    private int error;
    private String errorMessage;
    private double amount;
}
