package com.example.mobanko.activities.factory;

import static com.example.mobanko.activities.factory.AccountFactory.getAccount;

import com.example.mobanko.entities.User;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class UserFactory {

    public static User getUser(String userId, String name, String email, String phone) {
        var userData = new User();
        userData.setId(userId);
        userData.setName(name);
        userData.setCreationDate(LocalDateTime.now());
        userData.setEmail(email);
        userData.setPhoneNumber(phone);
        userData.setAccounts(new ArrayList<>());
        userData.getAccounts().add(getAccount(userId));
        return userData;
    }
}
