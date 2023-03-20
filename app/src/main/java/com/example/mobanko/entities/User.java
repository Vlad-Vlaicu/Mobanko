package com.example.mobanko.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
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
        return new User(1L, "name", "email", "000", new ArrayList<>());
    }
}
