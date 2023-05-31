package com.example.mobanko.entities;

import java.io.Serializable;

public enum AccountType implements Serializable {
    CURRENT_ACCOUNT, DEPOSIT;

    public static String getAccountTypeString(AccountType type) {
        switch (type) {
            case CURRENT_ACCOUNT: {
                return "Cont Curent";
            }

            case DEPOSIT: {
                return "Depozit";
            }
            default: {
                return "";
            }
        }
    }
}
