package com.example.oct_demo_1.model;

public class Company {
    private String Code;
    private String Name;

    public Company(String Code, String Name) {
        this.Code = Code;
        this.Name = Name;
    }

    public String getCode() {
        return Code;
    }

    public String getName() {
        return Name;
    }
}
