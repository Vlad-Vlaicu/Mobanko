package com.example.mobanko.generators;

public class IBANGenerator {
    public static String generateIban(String countryCode, String bankCode, String accountNumber) {
        String iban = countryCode.toUpperCase() + "00" + bankCode + accountNumber;

        // Convert letters to digits.
        StringBuilder digits = new StringBuilder();
        for (int i = 0; i < iban.length(); i++) {
            char c = iban.charAt(i);
            if (Character.isLetter(c)) {
                digits.append(Character.getNumericValue(c - 55));
            } else {
                digits.append(c);
            }
        }

        // Format the IBAN with the checksum.
        return countryCode.toUpperCase() + bankCode + accountNumber;
    }
}
