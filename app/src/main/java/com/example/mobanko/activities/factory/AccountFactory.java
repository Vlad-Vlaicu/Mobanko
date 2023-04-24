package com.example.mobanko.activities.factory;

import static com.example.mobanko.entities.AccountType.CURRENT_ACCOUNT;
import static com.example.mobanko.entities.CurrencyType.RON;
import static com.example.mobanko.generators.IBANGenerator.generateIban;
import static com.example.mobanko.generators.IdGenerator.generateID;
import static java.util.Collections.emptyList;

import com.example.mobanko.entities.Account;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class AccountFactory {
    public static Account getAccount(String userId) {

        var defaultAccount = new Account();
        defaultAccount.setAccountHolderID(userId);
        defaultAccount.setAccountType(CURRENT_ACCOUNT);
        defaultAccount.setCurrencyType(RON);
        defaultAccount.setBalance(500);
        defaultAccount.setTransactions(emptyList());
        defaultAccount.setCreationDate(LocalDateTime.now());
        defaultAccount.setIBAN(generateIban("40", "BCRO", generateID(8)));
        defaultAccount.setTransactions(new ArrayList<>());
        return defaultAccount;
    }
}
