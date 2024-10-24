package com.jarikkomarik.exchangeapi.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jarikkomarik.exchangeapi.util.CommaDoubleDeserializer;
import lombok.Data;

@Data
public class Row {
    private String kod;
    private String mena;
    private int mnozstvi;
    @JsonDeserialize(using = CommaDoubleDeserializer.class)
    private double kurz;
    private String zeme;
}