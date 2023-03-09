package com.example.mobanko.entities;

import java.util.List;

import lombok.Data;

@Data
public class User {

    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private List<Account> accounts;
}
