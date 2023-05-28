package com.example.mobanko.utils;

import static java.time.LocalDateTime.now;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TimeUtils {

    public static String getDynamicDate(LocalDateTime localDateTime) {

        if (localDateTime.getYear() == now().getYear() && localDateTime.getMonthValue() == now().getMonthValue()) {

            if (localDateTime.getDayOfMonth() == now().getDayOfMonth()) {
                return "Today";
            } else if (localDateTime.getDayOfMonth() == LocalDate.now().minusDays(1).getDayOfMonth()) {
                return "Yesterday";
            }
        }

        StringBuilder dateBuilder = new StringBuilder();
        dateBuilder.append(localDateTime.getDayOfMonth()).append("/").append(localDateTime.getMonthValue()).append("/").append(localDateTime.getYear());

        return dateBuilder.toString();
    }

    public static String getDynamicDate(String localDateTime) {
        return getDynamicDate(LocalDateTime.parse(localDateTime));
    }
}
