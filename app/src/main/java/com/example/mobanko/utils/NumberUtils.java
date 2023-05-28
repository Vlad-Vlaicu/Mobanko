package com.example.mobanko.utils;

public class NumberUtils {

    public static String getWholeValueFromDoubleAsString(Double number) {
        return String.valueOf(getWholeValueFromDouble(number));
    }

    public static Integer getWholeValueFromDouble(Double number) {
        return number.intValue();
    }

    public static String getFirstTwoDecimalsFromDouble(Double number) {
        double decimalPart = number - getWholeValueFromDouble(number);
        decimalPart = Math.round(decimalPart * 100.0) / 100.0;

        String decimalString = String.valueOf(decimalPart);

        if (decimalPart == 0) {
            decimalString = "00";
        }

        return decimalString;
    }


}
