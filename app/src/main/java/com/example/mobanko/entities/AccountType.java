package com.example.mobanko.entities;

public enum AccountType {
<<<<<<< HEAD
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
=======
    CURRENT_ACCOUNT, DEPOSIT
>>>>>>> 0d3af5b6b5f4e938afbcad13b0ffd030dfa0ec9d
}
