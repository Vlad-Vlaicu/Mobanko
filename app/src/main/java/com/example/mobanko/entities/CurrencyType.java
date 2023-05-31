package com.example.mobanko.entities;

import java.io.Serializable;

public enum CurrencyType implements Serializable {
    RON, EURO, DOLLAR;

    public static String getCurrencyString(CurrencyType currencyType) {
        return switch (currencyType) {
            case RON -> "RON";
            case EURO -> "EURO";
            case DOLLAR -> "DOLLAR";
        };
    }
}
