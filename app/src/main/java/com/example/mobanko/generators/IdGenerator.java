package com.example.mobanko.generators;

import java.security.SecureRandom;

public class IdGenerator {
    private static final String CHARACTERS = "0123456789";
    private static SecureRandom random = new SecureRandom();

    public static String generateID(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }
}
