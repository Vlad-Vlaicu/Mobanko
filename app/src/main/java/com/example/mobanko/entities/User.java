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

    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private List<Account> accounts;
    private LocalDateTime creationDate;
    private LocalDateTime accountValidatedDate;

    // TODO
    public static User getUserById(final Long id){
        var user = new User();
        user.setAccounts(new ArrayList<>());
        user.setId(1L);
        user.setName("nume");
        user.setEmail("email");
        user.setPhoneNumber("0000");
        return user;
    }
}
