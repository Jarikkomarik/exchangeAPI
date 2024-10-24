package com.jarikkomarik.exchangeapi.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class CNBExchangeRatesData {
    private String banka;
    private String datum;
    private int poradi;
    @JacksonXmlProperty(localName = "tabulka")
    private Table table;

}