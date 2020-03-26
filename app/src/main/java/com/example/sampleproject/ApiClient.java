package com.example.sampleproject;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String baseurl="https://cdash.boraksoftware.com/";
   public static Retrofit retrofit;
    public static Retrofit getApiClient(){

        if(retrofit==null){

            retrofit=new Retrofit.Builder().baseUrl(baseurl).addConverterFactory(GsonConverterFactory.create()).build();
        }
       return retrofit;

    }
}
