package com.jarikkomarik.exchangeapi.service;

import com.jarikkomarik.exchangeapi.dto.AmdorenExchangeRateData;
import com.jarikkomarik.exchangeapi.dto.CNBExchangeRatesData;
import com.jarikkomarik.exchangeapi.dto.CurrencyRateResponse;
import com.jarikkomarik.exchangeapi.dto.Row;
import com.jarikkomarik.exchangeapi.exceptions.DataRetrievalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

import static com.jarikkomarik.exchangeapi.Constants.*;
import static io.netty.util.internal.StringUtil.EMPTY_STRING;

@RequiredArgsConstructor
@Service
public class CurrencyRateService {

    private final WebClient amdorenClient;
    private final SupportedRatesService supportedRatesService;

    public Mono<CurrencyRateResponse> getRatesComparisonForRates(String firstRate, String secondRate) {
        return supportedRatesService.getCNBRatesData()
                .flatMap(cnbExchangeRatesData -> getSupportedCurrencyPair(cnbExchangeRatesData, firstRate, secondRate))
                .flatMap(this::getAmdorenRate);
    }

    private Mono<CurrencyRateResponse> getAmdorenRate(CurrencyRateResponse currencyRateResponse) {
        return amdorenClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("to", currencyRateResponse.getFirstCurrency())
                        .queryParam("from", currencyRateResponse.getSecondCurrency())
                        .build())
                .retrieve()
                .bodyToMono(AmdorenExchangeRateData.class)
                .flatMap(amdorenExchangeRateData -> {
                    if (0 != amdorenExchangeRateData.getError()) {
                        return Mono.error(new DataRetrievalException(CurrencyRateResponse.builder()
                                .errorMessage(FAILED_TO_GET_AMDOREN_RATES).build()));
                    }
                    currencyRateResponse.setAmdorenRate(amdorenExchangeRateData.getAmount());
                    currencyRateResponse.setCnbToAmdorenDiff(calculateCNBToAmdorenDiff(currencyRateResponse));
                    currencyRateResponse.setErrorMessage(EMPTY_STRING);
                    return Mono.just(currencyRateResponse);
                });
    }

    private double calculateCNBToAmdorenDiff(CurrencyRateResponse currencyRateResponse) {
        return BigDecimal.valueOf(currencyRateResponse.getCnbRate()).subtract(BigDecimal.valueOf(currencyRateResponse.getAmdorenRate())).doubleValue();
    }

    private Mono<CurrencyRateResponse> getSupportedCurrencyPair(CNBExchangeRatesData cnbExchangeRatesData, String firstRate, String secondRate) {
        Set<String> supportedCNBCurrencies = cnbExchangeRatesData.getTable().getRow()
                .stream()
                .map(Row::getKod)
                .collect(Collectors.toSet());

        if (firstRate.equalsIgnoreCase(CZK) && supportedCNBCurrencies.contains(secondRate.toUpperCase())) {
            double cnbRate = cnbExchangeRatesData.getTable().getRow()
                    .stream()
                    .filter(radek -> radek.getKod().equals(secondRate.toUpperCase()))
                    .findAny()
                    .orElseThrow()
                    .getKurz();
            return Mono.just(CurrencyRateResponse.builder()
                    .firstCurrency(firstRate)
                    .secondCurrency(secondRate)
                    .cnbRate(cnbRate)
                    .build());
        } else if (secondRate.equalsIgnoreCase(CZK) && supportedCNBCurrencies.contains(firstRate.toUpperCase())) {
            double cnbRate = cnbExchangeRatesData.getTable().getRow()
                    .stream()
                    .filter(radek -> radek.getKod().equals(firstRate.toUpperCase()))
                    .findAny()
                    .orElseThrow()
                    .getKurz();
            return Mono.just(CurrencyRateResponse.builder()
                    .firstCurrency(secondRate)
                    .secondCurrency(firstRate)
                    .cnbRate(cnbRate)
                    .build());
        } else {
            return Mono.error(new DataRetrievalException(CurrencyRateResponse.builder()
                    .errorMessage(NON_SUPPORTED_CURRENCY_PAIR)
                    .build()));
        }
    }
}