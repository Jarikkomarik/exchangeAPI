package com.jarikkomarik.exchangeapi.controller;

import com.jarikkomarik.exchangeapi.dto.CurrencyRateResponse;
import com.jarikkomarik.exchangeapi.service.CurrencyRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/")
public class CompareRatesController {

    private final CurrencyRateService currencyRateService;

    @GetMapping("rates-comparison")
    public Mono<CurrencyRateResponse> getRateComparison(@RequestParam String firstRate, @RequestParam String secondRate) {
        return currencyRateService.getRatesComparisonForRates(firstRate, secondRate);
    }

}
