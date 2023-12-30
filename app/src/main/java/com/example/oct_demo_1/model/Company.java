package com.example.oct_demo_1.model;

public class Company {
    private String Code;
    private String Name;


    public Company(String code, String name) {
        Code = code;
        Name = name;
    }

    public String getCode() {
        return Code;
    }

    public String getName() {
        return Name;
    }

}
