package com.example.mobanko.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Transaction implements Serializable {

    private Long ID;
    private Long recipientID;
    private Long senderID;
    private Double balance;
    private CurrencyType currencyType;
    private String dateOfTransaction;
    private String paymentNote;
}
