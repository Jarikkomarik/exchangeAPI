package com.jarikkomarik.exchangeapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.jarikkomarik.exchangeapi.service.SupportedRatesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/")
public class SupportedRatesController {

    private final SupportedRatesService supportedRatesService;

    @GetMapping(value = "supported-rates", produces = "application/json")
    public Mono<Set<String>> getSupportedRates() {
        return supportedRatesService.getSupportedCurrencies();
    }
}
