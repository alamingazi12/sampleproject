package com.example.sampleproject;

public class Group {

    String name;
    String date;
    String amount;

    public Group(String name, String date, String amount) {
        this.name = name;
        this.date = date;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getAmount() {
        return amount;
    }
}
