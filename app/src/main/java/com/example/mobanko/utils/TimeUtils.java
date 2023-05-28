package com.example.mobanko.utils;

import static java.time.LocalDateTime.now;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Locale;

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

    public static String getMonthAbbreviation(int monthIndex) {

        String[] shortMonths = new DateFormatSymbols().getShortMonths();

        // Format the date to get the 3-letter representation of the month
        return shortMonths[monthIndex];
    }

    public static String getCurrentMonthYearMinusMonths(int n) {
        // Create a Calendar instance
        Calendar calendar = Calendar.getInstance();

        // Subtract 'n' months from the current month
        calendar.add(Calendar.MONTH, -n);

        // Get the month and year
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        // Format the month as a full name with the first letter capitalized
        String monthName = new SimpleDateFormat("MMMM", Locale.ENGLISH).format(calendar.getTime());
        monthName = monthName.substring(0, 1).toUpperCase() + monthName.substring(1);

        // Format the month and year as a string
        String monthYear = monthName + " " + year;

        return monthYear;
    }
}
