package com.example.sampleproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserLogin extends AppCompatActivity {

     ArrayList<User> userinfoes;
       String cid;

     Button loginBtn;
     EditText username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        getSupportActionBar().setTitle("Login ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        username=findViewById(R.id.clientusername);
        password=findViewById(R.id.clientpassword);
        loginBtn=findViewById(R.id.button_login);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cusername=username.getText().toString();
                String cpassword=password.getText().toString();
                ApiInterface apiInterface= ApiClient.getApiClient().create(ApiInterface.class);
               Call<UserList> clientinfo= apiInterface.getClientInfo(cusername,cpassword);
               clientinfo.enqueue(new Callback<UserList>() {
                   @Override
                   public void onResponse(Call<UserList> call, Response<UserList> response) {
                       userinfoes=response.body().getUser();
                       if(!userinfoes.isEmpty()){
                           Intent intent=new Intent(UserLogin.this,MainActivity.class);
                          User user= userinfoes.get(0);
                          Bundle bundle=new Bundle();
                          bundle.putParcelable("obj",user);
                          intent.putExtras(bundle);
                           Toast.makeText(UserLogin.this,"Cid:"+userinfoes.get(0).getCid(),Toast.LENGTH_LONG).show();
                           startActivity(intent);

                       }else{

                           Toast.makeText(UserLogin.this,"Wrong username and password",Toast.LENGTH_LONG).show();

                       }
                   }

                   @Override
                   public void onFailure(Call<UserList> call, Throwable t) {

                   }
               });


            }
        });
    }
}
