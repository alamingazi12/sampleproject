package com.example.sampleproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PasswordActivity extends AppCompatActivity {

   String perusername;

    EditText old_password,new_pass,confirm_pass;
    Button changepass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       Bundle bundle= getIntent().getExtras();
            User usr =bundle.getParcelable("user");
            perusername=usr.getuNm();
       // Toast.makeText(PasswordActivity.this,"Username"+perusername,Toast.LENGTH_LONG).show();


        old_password=findViewById(R.id.oldpass);
        new_pass=findViewById(R.id.newpass);
        confirm_pass=findViewById(R.id.confirmpass);
        changepass=findViewById(R.id.btn_chang_pass);
        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldpassword=old_password.getText().toString();
              String  newpassowrd=new_pass.getText().toString();
              String confirmpassword=confirm_pass.getText().toString();
              if(TextUtils.isEmpty(oldpassword) || TextUtils.isEmpty(newpassowrd) || TextUtils.isEmpty(confirmpassword) || TextUtils.isEmpty(perusername)){

                  Toast.makeText(PasswordActivity.this,"One or more fields are Empty",Toast.LENGTH_LONG).show();
              }
              else{
                  if(newpassowrd.equals(confirmpassword)){

                      ApiInterface apiInterface=ApiClient.getApiClient().create(ApiInterface.class);
                      Call<PasswordInfo> passwordresult=apiInterface.getPasswordInfo(perusername,oldpassword,newpassowrd);
                      passwordresult.enqueue(new Callback<PasswordInfo>() {
                          @Override
                          public void onResponse(Call<PasswordInfo> call, Response<PasswordInfo> response) {
                              if(response.body().getMessage().equals("success")){


                                  Toast.makeText(PasswordActivity.this,"Password Changed Successfully",Toast.LENGTH_LONG).show();
                              }
                              else{

                                  Toast.makeText(PasswordActivity.this,"Something Wrong",Toast.LENGTH_LONG).show();
                              }
                          }

                          @Override
                          public void onFailure(Call<PasswordInfo> call, Throwable t) {

                          }
                      });


                  }
                  else
                  {

                      Toast.makeText(PasswordActivity.this,"password does not match",Toast.LENGTH_LONG).show();
                  }





              }



            }
        });

    }
}
