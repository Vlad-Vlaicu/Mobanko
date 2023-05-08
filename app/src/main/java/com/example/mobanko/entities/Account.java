package com.example.mobanko.entities;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Account implements Serializable {

    private String IBAN;
    private AccountType accountType;
    private double balance;
    private String accountHolderID;
    private CurrencyType currencyType;
    private List<Transaction> transactions;
    private String creationDate;
}
