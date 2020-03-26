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

public class UpdateActivity extends AppCompatActivity {

    EditText upcname,uppass,upstatus;

    String uname;
    Client clientobj;
    Button button_up;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Bundle bundle=getIntent().getExtras();
        clientobj= bundle.getParcelable("client");

        uname=clientobj.getuNm();


        initializeAll();
        button_up=findViewById(R.id.update_btn);
        button_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              String cname= upcname.getText().toString();
              String pass=uppass.getText().toString();
              String status=upstatus.getText().toString();

              if(TextUtils.isEmpty(cname)|| TextUtils.isEmpty(pass) || TextUtils.isEmpty(status) || TextUtils.isEmpty(uname))
              {

                  Toast.makeText(UpdateActivity.this,"One or more fields are empty",Toast.LENGTH_LONG).show();

              }
              else {

                   ApiInterface apiInterface=ApiClient.getApiClient().create(ApiInterface.class);
                   Call<UpdateInfo> update=apiInterface.getupdateInfo(cname,uname,pass,status);
                   update.enqueue(new Callback<UpdateInfo>() {
                       @Override
                       public void onResponse(Call<UpdateInfo> call, Response<UpdateInfo> response) {
                           String message=response.body().getMessage();

                           if(message.equals("success")){


                               Toast.makeText(UpdateActivity.this,"Information Updated Successfully",Toast.LENGTH_LONG).show();
                           }
                           else {

                               Toast.makeText(UpdateActivity.this,"Something Wrong Please Try Again",Toast.LENGTH_LONG).show();
                           }
                       }

                       @Override
                       public void onFailure(Call<UpdateInfo> call, Throwable t) {

                       }
                   });



              }
            }
        });
    }

    private void initializeAll() {
        upcname=findViewById(R.id.update_client);
        uppass=findViewById(R.id.update_pass);
        upstatus=findViewById(R.id.update_status);
        upcname.setText(clientobj.getcNm());
        uppass.setText(clientobj.getuPs());
        upstatus.setText(clientobj.getuSt());
    }
}
