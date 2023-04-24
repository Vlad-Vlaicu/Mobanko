package com.example.mobanko.entities;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Account {

    private String IBAN;
    private AccountType accountType;
    private double balance;
    private String accountHolderID;
    private CurrencyType currencyType;
    private List<Transaction> transactions;
    private LocalDateTime creationDate;
}
