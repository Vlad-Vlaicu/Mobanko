package com.example.mobanko.entities;

import lombok.Data;

@Data
public class Template {

    private String paymentNote;
    private String templateName;
    private String recipientName;
    private Double amount;
    private CurrencyType currencyType;
}
