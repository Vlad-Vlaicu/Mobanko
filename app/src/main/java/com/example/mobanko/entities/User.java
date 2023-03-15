package com.example.mobanko.entities;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class User {

    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private List<Account> accounts;
}
