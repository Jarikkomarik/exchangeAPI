package com.jarikkomarik.exchangeapi.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import java.util.List;

@Data
public class Table {
    private String typ;
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "radek")
    private List<Row> row;

}
