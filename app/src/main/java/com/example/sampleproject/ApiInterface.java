package com.example.sampleproject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

  @GET("fetchdata.php")
    Call<InfoList> getInfoList(@Query("date") String date,@Query("cid") String cid);

  @GET("fetchdata2.php")
  Call<InfoList> getInfoList2(@Query("date") String date,@Query("cid") String cid);
  @GET("signin.php")
  Call<UserList> getClientInfo(@Query("username") String username,@Query("password") String password);

  @GET("registration.php")
  Call<SignResult> getMessageInfo(@Query("cNm") String cNm,@Query("uNm") String uNm,@Query("uPs") String uPs,@Query("uSt") String uSt);

  @GET("update.php")
  Call<UpdateInfo> getupdateInfo(@Query("cNm") String cNm,@Query("uNm") String uNm,@Query("uPs") String uPs,@Query("uSt") String uSt);

  @GET("changepass.php")
  Call<PasswordInfo> getPasswordInfo(@Query("uNm") String uNm,@Query("oldpass") String oldpass,@Query("newpass") String newpass);



}
