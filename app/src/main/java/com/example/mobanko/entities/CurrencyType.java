package com.example.mobanko.entities;

public enum CurrencyType {
    RON, EURO, DOLLAR;

    public static String getCurrencyString(CurrencyType currencyType)
    {
        switch (currencyType)
        {
            case RON: return "RON";
            case EURO: return "EURO";
            case DOLLAR: return "DOLLAR";
            default: return "";
        }
    }
}
