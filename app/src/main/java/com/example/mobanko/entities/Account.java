package com.example.mobanko.entities;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Account {

    private String IBAN;
    private AccountType accountType;
    private double balance;
    private Long accountHolderID;
    private CurrencyType currencyType;
    private List<Transaction> transactions;
}
