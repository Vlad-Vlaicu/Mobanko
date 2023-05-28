package com.example.mobanko.entities;

import java.util.List;

public enum Subcategories {
    SALARY, PENSION, BUSINESS_INCOME, UNEMPLOYMENT, MATERNITY, FAMILY_ALLOWANCE, ALIMONY, OTHER,
    RENTAL, CASH_DEPOSIT, TRAVEL_EXPENSES_OVERTIME, OWN_TRANSFER, ONLINE_SHOPS, REIMBURSEMENTS,
    INCOME_SAVING_INVESTMENTS, INCOME_INSURANCE, CRYPTO, INVESTMENT_INCOME, SAVINGS_INCOME, OTHER_INCOME,
    RENT, HOUSE_LOAN, ENERGY, UTILITY_BILLS, WASTE_DISPOSAL, SEWER_FEES, INSURANCE, IMPROVEMENTS_N_FURNITURE,
    CLEANING, PET_CARE, PROPERTY_INSURANCE, PERSONAL_ITEM_INSURANCE, OTHER_LIVING_N_ENERGY, GROCERIES, RESTAURANT_CAFE,
    OTHER_FOOD, DRUGSTORE, PHARMACY_N_HEALTHCARE, COSMETICS_N_EYE_CARE, DOCTORS_N_HOSPITAL, LIFE_N_HEALTH_INSURANCE,
    LIFE_INSURANCE, ACCIDENT_INSURANCE, COSMETICS_N_APPEARANCE, HEALTH_INSURANCE, RISK_LIFE_INSURANCE, OTHER_HEALTH_N_SELFCARE,
    FASHION, SHOES, ACCESSORIES, PHONE_BILLS, TELEVISION, INTERNET, ENTERTAINMENT_ELECTRONICS, AMUSEMENT, HOBBIES, READING,
    TOYS_N_GAMES, TOBACCONIST, BETTING, OTHER_LEISURE_N_HOBBIES, SCHOOL, BOOKS_N_SUPPLIES, TUITION, OTHER_EDUCATION,
    PURCHASE_LOAN, GAS_N_FUEL, MAINTENANCE_N_SERVICE, PARKING, VIGNETTE_TOLLS, OTHER_CAR_EXPENSES, PUBLIC_TRANSPORT, TAXI,
    OTHER_TRANSPORT_EXPENSES, ACCOMMODATION, FOOD_DRINKS, TRAVEL_EXPENSES, FOREIGN_CURRENCIES, TRAVEL_INSURANCE,
    OTHER_HOLIDAY_EXPENSES, ATM_WITHDRAWAL, BRANCH_WITHDRAWAL, CREDIT_CARD_WITHDRAWAL, CREDIT_CARD_BILL, QUICK_TOP_UPS,
    CASH_WITHDRAWAL, OTHER_CASH, POCKET_MONEY, PRIVATE_CONTRIBUTION, AMAZON, GOOGLE, WEB_SERVICES, UNCATEGORIZED_ONLINE_SHOPS,
    SAVINGS, BUILDING_SOCIETY, CAPITALIZATION_PRODUCTS, SECURITIES, CRYPTOCURRENCIES, INVESTMENT_PLAN, NOBLE_METALS,
    OTHER_SAVINGS, PERSONAL_INCOME_TAX, VALUE_ADDED_TAX, OTHER_TAXES, INTERNAL_TRANSFERS, FEES, INTERESTS, LENDING,
    EXTERNAL_OWN_TRANSFERS, TAXES, OTHER_FEES, FINANCING, INSURANCE_PREMIUM, SOCIAL_INSURANCE, GIFTS, DONATIONS,
    PENALTIES_N_GOVERNMENTS, DEBT_COLLECTION, DONATIONS_N_ASSOCIATIONS, LIABILITY_INSURANCE, PAYMENT_PROTECTION_INSURANCE,
    INCOME_PROTECTION_INSURANCE, OTHER_VARIOUS_EXPENSES, UNCATEGORIZED;

    public static List<Subcategories> getSubcategories(Categories categories) {
        return switch (categories) {
            case LIVING_N_ENERGY ->
                    List.of(RENT, HOUSE_LOAN, ENERGY, UTILITY_BILLS, WASTE_DISPOSAL, SEWER_FEES, INSURANCE,
                            IMPROVEMENTS_N_FURNITURE, CLEANING, PET_CARE, PROPERTY_INSURANCE, PERSONAL_ITEM_INSURANCE, OTHER_LIVING_N_ENERGY);
            case FOOD_N_DINING -> List.of(GROCERIES, RESTAURANT_CAFE, OTHER_FOOD);
            case HEALTH_N_SELFCARE ->
                    List.of(DRUGSTORE, PHARMACY_N_HEALTHCARE, COSMETICS_N_EYE_CARE, DOCTORS_N_HOSPITAL,
                            LIFE_N_HEALTH_INSURANCE, LIFE_INSURANCE, ACCIDENT_INSURANCE, COSMETICS_N_APPEARANCE, HEALTH_INSURANCE,
                            RISK_LIFE_INSURANCE, OTHER_HEALTH_N_SELFCARE);
            case CLOTHING_N_SHOES -> List.of(FASHION, SHOES, ACCESSORIES);
            case COMMUNICATION_N_MEDIA ->
                    List.of(PHONE_BILLS, TELEVISION, INTERNET, ENTERTAINMENT_ELECTRONICS);
            case LEISURE_N_HOBBIES ->
                    List.of(AMUSEMENT, HOBBIES, READING, TOYS_N_GAMES, TOBACCONIST, BETTING, OTHER_LEISURE_N_HOBBIES);
            case EDUCATION -> List.of(SCHOOL, BOOKS_N_SUPPLIES, TUITION, OTHER_EDUCATION);
            case CAR ->
                    List.of(PURCHASE_LOAN, GAS_N_FUEL, MAINTENANCE_N_SERVICE, INSURANCE, PARKING, VIGNETTE_TOLLS, OTHER_CAR_EXPENSES);
            case PUBLIC_TRANSPORT_N_TAXI ->
                    List.of(PUBLIC_TRANSPORT, TAXI, OTHER_TRANSPORT_EXPENSES);
            case HOLIDAYS_N_TRAVEL ->
                    List.of(ACCOMMODATION, FOOD_DRINKS, TRAVEL_EXPENSES, FOREIGN_CURRENCIES, TRAVEL_INSURANCE,
                            OTHER_HOLIDAY_EXPENSES);
            case WITHDRAWAL ->
                    List.of(ATM_WITHDRAWAL, BRANCH_WITHDRAWAL, CREDIT_CARD_WITHDRAWAL, CREDIT_CARD_BILL, QUICK_TOP_UPS,
                            CASH_WITHDRAWAL, OTHER_CASH);
            case ALIMONY_N_POCKET_MONEY ->
                    List.of(ALIMONY, POCKET_MONEY, PRIVATE_CONTRIBUTION, OTHER);
            case ONLINE_SHOPS ->
                    List.of(AMAZON, GOOGLE, ONLINE_SHOPS, WEB_SERVICES, UNCATEGORIZED_ONLINE_SHOPS);
            case SAVING_N_INVESTMENT ->
                    List.of(SAVINGS, BUILDING_SOCIETY, CAPITALIZATION_PRODUCTS, SECURITIES, CRYPTOCURRENCIES,
                            INVESTMENT_PLAN, NOBLE_METALS, OTHER_SAVINGS);
            case TAXES -> List.of(PERSONAL_INCOME_TAX, VALUE_ADDED_TAX, OTHER_TAXES);
            case TRANSACTIONS_N_FEES ->
                    List.of(INTERNAL_TRANSFERS, FEES, INTERESTS, LENDING, EXTERNAL_OWN_TRANSFERS, TAXES, OTHER_FEES);
            case OTHER_EXPENSES ->
                    List.of(FINANCING, INSURANCE_PREMIUM, SOCIAL_INSURANCE, GIFTS, DONATIONS, PENALTIES_N_GOVERNMENTS,
                            DEBT_COLLECTION, DONATIONS_N_ASSOCIATIONS, LIABILITY_INSURANCE, PAYMENT_PROTECTION_INSURANCE, INCOME_PROTECTION_INSURANCE,
                            OTHER_VARIOUS_EXPENSES);
            case UNCATEGORIZED_EXPENSES -> List.of(UNCATEGORIZED);
            case INCOME ->
                    List.of(SALARY, PENSION, BUSINESS_INCOME, UNEMPLOYMENT, MATERNITY, FAMILY_ALLOWANCE,
                            ALIMONY, OTHER);
            case ADDITIONAL_INCOME ->
                    List.of(RENTAL, CASH_DEPOSIT, TRAVEL_EXPENSES_OVERTIME, OWN_TRANSFER, ONLINE_SHOPS,
                            REIMBURSEMENTS, INCOME_SAVING_INVESTMENTS, INCOME_INSURANCE, CRYPTO, INVESTMENT_INCOME,
                            SAVINGS_INCOME, OTHER);
            case UNCATEGORIZED_INCOME -> List.of(OTHER_INCOME);
        };
    }

    public static String getSubcategoryString(Subcategories subcategories) {
        return switch (subcategories) {

            case SALARY -> "Salary";
            case PENSION -> "Pension";
            case BUSINESS_INCOME -> "Business income";
            case UNEMPLOYMENT -> "Unemployment compensation";
            case MATERNITY -> "Maternity leave";
            case FAMILY_ALLOWANCE -> "Family allowance";
            case ALIMONY -> "Alimony";
            case OTHER -> "Other";
            case RENTAL -> "Rental";
            case CASH_DEPOSIT -> "Cash deposit";
            case TRAVEL_EXPENSES_OVERTIME -> "Travel expenses / Overtime";
            case OWN_TRANSFER -> "Own Transfer";
            case ONLINE_SHOPS -> "Online Shops";
            case REIMBURSEMENTS -> "Reimbursement";
            case INCOME_SAVING_INVESTMENTS -> "Income from savings & investments";
            case INCOME_INSURANCE -> "Income from insurances";
            case CRYPTO -> "Crypto Income";
            case INVESTMENT_INCOME -> "Investment Income";
            case SAVINGS_INCOME -> "Savings Income";
            case OTHER_INCOME -> "Other income";
            case RENT -> "Rent";
            case HOUSE_LOAN -> "House loan";
            case ENERGY -> "Energy";
            case UTILITY_BILLS -> "Utility bills";
            case WASTE_DISPOSAL -> "Waste disposal";
            case SEWER_FEES -> "Sewer fees";
            case INSURANCE -> "Insurance";
            case IMPROVEMENTS_N_FURNITURE -> "Improvements & Furniture";
            case CLEANING -> "Cleaning";
            case PET_CARE -> "Pet-care";
            case PROPERTY_INSURANCE -> "Property Insurance";
            case PERSONAL_ITEM_INSURANCE -> "Personal Item Insurance";
            case OTHER_LIVING_N_ENERGY -> "Other Living & Energy";
            case GROCERIES -> "Groceries";
            case RESTAURANT_CAFE -> "Restaurant/Café";
            case OTHER_FOOD -> "Other Food";
            case DRUGSTORE -> "Drugstores";
            case PHARMACY_N_HEALTHCARE -> "Pharmacy & Healthcare";
            case COSMETICS_N_EYE_CARE -> "Cosmetics & Eye-care";
            case DOCTORS_N_HOSPITAL -> "Doctors & Hospital";
            case LIFE_N_HEALTH_INSURANCE -> "Life & Health insurance";
            case LIFE_INSURANCE -> "Life Insurance";
            case ACCIDENT_INSURANCE -> "Accident Insurance";
            case COSMETICS_N_APPEARANCE -> "Cosmetics & Appearance";
            case HEALTH_INSURANCE -> "Health Insurance";
            case RISK_LIFE_INSURANCE -> "Risk Life Insurance";
            case OTHER_HEALTH_N_SELFCARE -> "Other Health & Self-care";
            case FASHION -> "Fashion";
            case SHOES -> "Shoes";
            case ACCESSORIES -> "Accessories";
            case PHONE_BILLS -> "Phone bills";
            case TELEVISION -> "Television/ORF";
            case INTERNET -> "Electronic media/internet";
            case ENTERTAINMENT_ELECTRONICS -> "Entertainment electronics";
            case AMUSEMENT -> "Amusement";
            case HOBBIES -> "Hobbies/Sport";
            case READING -> "Reading";
            case TOYS_N_GAMES -> "Toys & Games";
            case TOBACCONIST -> "Tobacconist's";
            case BETTING -> "Betting";
            case OTHER_LEISURE_N_HOBBIES -> "Other Leisure & Hobbies";
            case SCHOOL -> "Kindergarten/School/University";
            case BOOKS_N_SUPPLIES -> "Books & Supplies";
            case TUITION -> "Tuition";
            case OTHER_EDUCATION -> "Other Education";
            case PURCHASE_LOAN -> "Purchase/Loan";
            case GAS_N_FUEL -> "Gas & Fuel";
            case MAINTENANCE_N_SERVICE -> "Maintenance/Service";
            case PARKING -> "Parking";
            case VIGNETTE_TOLLS -> "Vignette Tolls";
            case OTHER_CAR_EXPENSES -> "Other Car Expenses";
            case PUBLIC_TRANSPORT -> "Public transport";
            case TAXI -> "Taxi";
            case OTHER_TRANSPORT_EXPENSES -> "Other Transport Expenses";
            case ACCOMMODATION -> "Accommodation";
            case FOOD_DRINKS -> "Food/Drinks";
            case TRAVEL_EXPENSES -> "Travel expenses";
            case FOREIGN_CURRENCIES -> "Foreign currencies";
            case TRAVEL_INSURANCE -> "Travel Insurance";
            case OTHER_HOLIDAY_EXPENSES -> "Other Holiday Expenses";
            case ATM_WITHDRAWAL -> "ATM withdrawal";
            case BRANCH_WITHDRAWAL -> "Branch withdrawal";
            case CREDIT_CARD_WITHDRAWAL -> "Credit Card withdrawal";
            case CREDIT_CARD_BILL -> "Credit Card bill";
            case QUICK_TOP_UPS -> "Quick top ups";
            case CASH_WITHDRAWAL -> "Cash Withdrawal";
            case OTHER_CASH -> "Other Cash";
            case POCKET_MONEY -> "Pocket money";
            case PRIVATE_CONTRIBUTION -> "Private Contribution";
            case AMAZON -> "Amazon";
            case GOOGLE -> "Google";
            case WEB_SERVICES -> "Web Services";
            case UNCATEGORIZED_ONLINE_SHOPS -> "Uncategorized online shops";
            case SAVINGS -> "Savings";
            case BUILDING_SOCIETY -> "Building society";
            case CAPITALIZATION_PRODUCTS -> "Capitalization products";
            case SECURITIES -> "Securities";
            case CRYPTOCURRENCIES -> "Cryptocurrencies";
            case INVESTMENT_PLAN -> "Investment Plan";
            case NOBLE_METALS -> "Noble Metals";
            case OTHER_SAVINGS -> "Other savings";
            case PERSONAL_INCOME_TAX -> "Personal Income Tax";
            case VALUE_ADDED_TAX -> "Value Added Tax";
            case OTHER_TAXES -> "Other Taxes";
            case INTERNAL_TRANSFERS -> "Internal transfers";
            case FEES -> "Fees";
            case INTERESTS -> "Interests";
            case LENDING -> "Lending";
            case EXTERNAL_OWN_TRANSFERS -> "External Own Transfers";
            case TAXES -> "Taxes";
            case OTHER_FEES -> "Other Fees";
            case FINANCING -> "Financing";
            case INSURANCE_PREMIUM -> "Insurance Premium";
            case SOCIAL_INSURANCE -> "Social Insurance";
            case GIFTS -> "Gifts";
            case DONATIONS -> "Donations";
            case PENALTIES_N_GOVERNMENTS -> "Penalties & Governments";
            case DEBT_COLLECTION -> "Debt collection";
            case DONATIONS_N_ASSOCIATIONS -> "Donations & Associations";
            case LIABILITY_INSURANCE -> "Liability Insurance";
            case PAYMENT_PROTECTION_INSURANCE -> "Payment Protection Insurance";
            case INCOME_PROTECTION_INSURANCE -> "Income Protection Insurance";
            case OTHER_VARIOUS_EXPENSES -> "Other various Expenses";
            case UNCATEGORIZED -> "Uncategorized";
        };
    }
}
