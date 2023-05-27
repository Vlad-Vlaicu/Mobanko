package com.example.mobanko.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Transaction implements Serializable {

    private String ID;
    private String recipientID;
    private String senderID;
    private Double balance;
    private CurrencyType currencyType;
    private String dateOfTransaction;
    private String paymentNote;
    private PaymentType paymentType;
    private Categories categories;
    private Subcategories subcategories;
}
