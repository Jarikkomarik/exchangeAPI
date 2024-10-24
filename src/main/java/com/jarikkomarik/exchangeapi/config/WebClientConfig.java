package com.jarikkomarik.exchangeapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.Map;

@Configuration
public class WebClientConfig {

    @Value("${amdoren.apikey}")
    private String apiKey;

    @Bean
    public WebClient cnbClient() {
        return WebClient.builder()
                .baseUrl("https://www.cnb.cz/cs/financni_trhy/devizovy_trh/kurzy_devizoveho_trhu/denni_kurz.xml")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE)
                .build();
    }

    @Bean
    public WebClient amdorenClient() {
        return WebClient.builder()
                .baseUrl("https://www.amdoren.com/api/currency.php?api_key=" + apiKey)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
