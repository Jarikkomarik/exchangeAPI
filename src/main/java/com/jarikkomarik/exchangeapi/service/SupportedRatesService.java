package com.jarikkomarik.exchangeapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.jarikkomarik.exchangeapi.dto.CNBExchangeRatesData;
import com.jarikkomarik.exchangeapi.dto.Row;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.stream.Collectors;

import static com.jarikkomarik.exchangeapi.Constants.CZK;

@RequiredArgsConstructor
@Service
public class SupportedRatesService {


    private final WebClient cnbClient;
    private final XmlMapper xmlMapper;

    public Mono<Set<String>> getSupportedCurrencies() {
        return cnbClient.get()
                .retrieve()
                .bodyToMono(String.class)
                .map(this::parseXML)
                .map(this::getCurrencyPairs);
    }

    public Mono<CNBExchangeRatesData> getCNBRatesData() {
        return cnbClient.get()
                .retrieve()
                .bodyToMono(String.class)
                .map(this::parseXML);
    }

    private CNBExchangeRatesData parseXML(String xml) {
        try {
            return xmlMapper.readValue(xml, CNBExchangeRatesData.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private Set<String> getCurrencyPairs(CNBExchangeRatesData CNBExchangeRatesData) {

        return CNBExchangeRatesData.
                getTable()
                .getRow()
                .stream()
                .map(Row::getKod)
                .map(this::buildPair)
                .collect(Collectors.toSet());
    }

    private String buildPair(String currency) {
        return CZK + ":" + currency;
    }

}
