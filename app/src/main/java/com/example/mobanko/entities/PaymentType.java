package com.example.mobanko.entities;

public enum PaymentType {
    INTRABANK, PLATA_LICHIDARE, CARD;

    public static String getPaymentString(PaymentType paymentType) {
        return switch (paymentType) {
            case INTRABANK -> "Intrabank payment order";
            case PLATA_LICHIDARE -> "plata lichidare";
            case CARD -> "Payment with card";
        };
    }
}
