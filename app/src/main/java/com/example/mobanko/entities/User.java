package com.example.mobanko.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class User implements Serializable {

    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private ArrayList<Account> accounts;
    private LocalDateTime creationDate;
    private LocalDateTime accountValidatedDate;

    // TODO
    public static User getUserById(final String id) {
        var user = new User();
        user.setAccounts(new ArrayList<>());
        user.setId("1");
        user.setName("nume");
        user.setEmail("email");
        user.setPhoneNumber("0000");
        return user;
    }
}
