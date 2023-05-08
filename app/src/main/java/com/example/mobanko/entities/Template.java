package com.example.mobanko.entities;

import java.io.Serializable;

import lombok.Data;

@Data
public class Template implements Serializable {

    private String paymentNote;
    private String templateName;
    private String recipientName;
    private Double amount;
    private CurrencyType currencyType;
}
