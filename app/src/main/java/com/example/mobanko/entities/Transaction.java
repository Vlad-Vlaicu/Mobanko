package com.example.mobanko.entities;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Transaction {

    private Long ID;
    private Long recipientID;
    private Long senderID;
    private Double balance;
    private CurrencyType currencyType;
    private LocalDateTime dateOfTransaction;
    private String paymentNote;
}
