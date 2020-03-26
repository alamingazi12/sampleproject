package com.example.sampleproject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class InfoList {

    @SerializedName("result")
    @Expose
    ArrayList<Details> details=new ArrayList<>();

    public ArrayList<Details> getDetails() {
        return details;
    }

    public String message;

    public String getMessage() {
        return message;
    }
}
