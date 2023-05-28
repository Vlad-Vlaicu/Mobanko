package com.example.mobanko.entities;


import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.core.content.res.ResourcesCompat;

import com.example.mobanko.R;

import java.util.ArrayList;
import java.util.List;

public enum Categories {
    LIVING_N_ENERGY, FOOD_N_DINING, HEALTH_N_SELFCARE, CLOTHING_N_SHOES, COMMUNICATION_N_MEDIA,
    LEISURE_N_HOBBIES, EDUCATION, CAR, PUBLIC_TRANSPORT_N_TAXI, HOLIDAYS_N_TRAVEL, WITHDRAWAL,
    ALIMONY_N_POCKET_MONEY, ONLINE_SHOPS, SAVING_N_INVESTMENT, TAXES, TRANSACTIONS_N_FEES,
    OTHER_EXPENSES, UNCATEGORIZED_EXPENSES, INCOME, ADDITIONAL_INCOME, UNCATEGORIZED_INCOME;

    public static List<Categories> getIncomeCategories() {
        return List.of(INCOME, ADDITIONAL_INCOME, UNCATEGORIZED_INCOME);
    }

    public static List<Drawable> getIncomeCategoriesIcons(Context context) {
        List<Drawable> iconList = new ArrayList<>();
        iconList.add(ResourcesCompat.getDrawable(context.getResources(), R.mipmap.income_icon, null));
        iconList.add(ResourcesCompat.getDrawable(context.getResources(), R.mipmap.additional_income_icon, null));
        iconList.add(ResourcesCompat.getDrawable(context.getResources(), R.mipmap.uncategorised_income_icon, null));

        return iconList;
    }

    public static List<Categories> getExpensesCategories() {
        return List.of(LIVING_N_ENERGY, FOOD_N_DINING, HEALTH_N_SELFCARE, CLOTHING_N_SHOES,
                COMMUNICATION_N_MEDIA, LEISURE_N_HOBBIES, EDUCATION, CAR, PUBLIC_TRANSPORT_N_TAXI,
                HOLIDAYS_N_TRAVEL, WITHDRAWAL, ALIMONY_N_POCKET_MONEY, ONLINE_SHOPS, SAVING_N_INVESTMENT,
                TAXES, TRANSACTIONS_N_FEES, OTHER_EXPENSES, UNCATEGORIZED_EXPENSES);
    }

    public static List<Drawable> getExpensesCategoriesIcons(Context context) {
        List<Drawable> iconList = new ArrayList<>();
        iconList.add(ResourcesCompat.getDrawable(context.getResources(), R.mipmap.living_n_energy, null));
        iconList.add(ResourcesCompat.getDrawable(context.getResources(), R.mipmap.food_n_dining, null));
        iconList.add(ResourcesCompat.getDrawable(context.getResources(), R.mipmap.healthcare_icon, null));
        iconList.add(ResourcesCompat.getDrawable(context.getResources(), R.mipmap.clothing_icon, null));
        iconList.add(ResourcesCompat.getDrawable(context.getResources(), R.mipmap.media_icon, null));
        iconList.add(ResourcesCompat.getDrawable(context.getResources(), R.mipmap.leisure_icon, null));
        iconList.add(ResourcesCompat.getDrawable(context.getResources(), R.mipmap.education_icon, null));
        iconList.add(ResourcesCompat.getDrawable(context.getResources(), R.mipmap.car_icon, null));
        iconList.add(ResourcesCompat.getDrawable(context.getResources(), R.mipmap.public_transport_icon, null));
        iconList.add(ResourcesCompat.getDrawable(context.getResources(), R.mipmap.holiday_icon, null));
        iconList.add(ResourcesCompat.getDrawable(context.getResources(), R.mipmap.withdraw_icon, null));
        iconList.add(ResourcesCompat.getDrawable(context.getResources(), R.mipmap.alimony_icon, null));
        iconList.add(ResourcesCompat.getDrawable(context.getResources(), R.mipmap.online_shops_icon, null));
        iconList.add(ResourcesCompat.getDrawable(context.getResources(), R.mipmap.savings_icon, null));
        iconList.add(ResourcesCompat.getDrawable(context.getResources(), R.mipmap.taxes_icon, null));
        iconList.add(ResourcesCompat.getDrawable(context.getResources(), R.mipmap.transactions_icon, null));
        iconList.add(ResourcesCompat.getDrawable(context.getResources(), R.mipmap.other_expenses_icon, null));
        iconList.add(ResourcesCompat.getDrawable(context.getResources(), R.mipmap.uncategorised_expenses_icon, null));

        return iconList;
    }

    public static String getCategoryString(Categories categories) {
        return switch (categories) {
            case LIVING_N_ENERGY -> "Living & Energy";
            case FOOD_N_DINING -> "Food & Dining";
            case HEALTH_N_SELFCARE -> "Health & Self-care";
            case CLOTHING_N_SHOES -> "Clothing & Shoes";
            case COMMUNICATION_N_MEDIA -> "Communication & Media";
            case LEISURE_N_HOBBIES -> "Leisure & Hobbies";
            case EDUCATION -> "Education";
            case CAR -> "Car";
            case PUBLIC_TRANSPORT_N_TAXI -> "Public transport & Taxi";
            case HOLIDAYS_N_TRAVEL -> "Holidays & Travel";
            case WITHDRAWAL -> "Withdrawal";
            case ALIMONY_N_POCKET_MONEY -> "Alimony & Pocket money";
            case ONLINE_SHOPS -> "Online Shops";
            case SAVING_N_INVESTMENT -> "Savings & Investment";
            case TAXES -> "Taxes";
            case TRANSACTIONS_N_FEES -> "Transactions & Fees";
            case OTHER_EXPENSES -> "Other expense";
            case UNCATEGORIZED_EXPENSES -> "Uncategorized expenses";
            case INCOME -> "Income";
            case ADDITIONAL_INCOME -> "Additional Income";
            case UNCATEGORIZED_INCOME -> "Uncategorized income";
        };
    }
}
