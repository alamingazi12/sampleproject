package com.example.sampleproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registration extends AppCompatActivity implements AdapterView.OnItemSelectedListener{


    String[] status={"Select here","1","0"};
   String finalstatus;
    ArrayList<Client> clients=new ArrayList<>();
    RequestQueue requestQueue;
    RecyclerView recyclerViewfetchdata;
    Spinner spinner_sttus;

    String curl="https://cdash.boraksoftware.com/clientdata.php";

    String number="";
    String alpha="";
    String special="";
    String pass;
    EditText clientname,clusername,clientpass;
    Button user_register_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Toolbar toolbar=findViewById(R.id.toolbar_reg);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        initializeAll();

        user_register_btn=findViewById(R.id.usersignup);
        requestQueue= Volley.newRequestQueue(this);
        clientpass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i4, int i1, int i2) {


                String s = clientpass.getText().toString();

                //Toast.makeText(MainActivity.this,""+s,Toast.LENGTH_LONG).show();
                number="";
                special="";
                alpha="";

                for(int i=0; i<s.length(); i++){

                    if(Character.isDigit(s.charAt(i))) {

                        number=number+s.charAt(i);
                    }
                    else if(Character.isAlphabetic(s.charAt(i))) {

                        alpha=alpha+s.charAt(i);
                    }
                    else {
                        special=special+s.charAt(i);

                    }


                }
                if(s.length()>4){


                    if(special.length()>=1 && number.length()>=1 && alpha.length()>=1){

                        if(s.length()<7) {
                            Toast.makeText(Registration.this,"password character should be more than seven",Toast.LENGTH_SHORT).show();
                        }
                        else{

                            pass=s;
                            Toast.makeText(Registration.this,"password:"+pass,Toast.LENGTH_SHORT).show();

                        }


                    }
                    else {

                        Toast.makeText(Registration.this,"Password must contains one letter,one digit and one special character",Toast.LENGTH_SHORT).show();
                    }


                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        recyclerViewfetchdata=findViewById(R.id.itemshowrecyclerview);
        recyclerViewfetchdata.setHasFixedSize(true);
        recyclerViewfetchdata.setLayoutManager(new LinearLayoutManager(this));
        user_register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();

            }
        });

        jSonParsing();


    }

    private void jSonParsing() {
       // Toast.makeText(Registration.this,"json parsing calling",Toast.LENGTH_LONG).show();
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, curl, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray=response.getJSONArray("results");

                      for(int i=0;i<jsonArray.length();i++){

                              JSONObject hit=jsonArray.getJSONObject(i);
                             String cid= hit.getString("cid");
                          String cNm= hit.getString("cNm");
                          String uNm= hit.getString("uNm");
                          String uPs= hit.getString("uPs");
                          String uSt= hit.getString("uSt");
                      clients.add(new Client(cid,cNm,uNm,uPs,uSt));
                      }
                      if(clients.size()>0){
                          String name=clients.get(0).getcNm();
                          Toast.makeText(Registration.this,"data"+name,Toast.LENGTH_LONG).show();
                          findViewById(R.id.itemprogress).setVisibility(View.GONE);
                          MyShowAdapter myShowAdapter=new MyShowAdapter(clients,Registration.this);
                          myShowAdapter.setOnItemClickListener(new MyShowAdapter.OnItemClickListener() {
                              @Override
                              public void onItemClick(int position) {
                                  Client client=clients.get(position);
                                  Intent intent=new Intent(Registration.this,UpdateActivity.class);
                                  Bundle bundle=new Bundle();
                                  bundle.putParcelable("client",client);
                                  intent.putExtras(bundle);
                                  startActivity(intent);
                              }
                          });
                          recyclerViewfetchdata.setAdapter(myShowAdapter);
                      }
                      else {
                          findViewById(R.id.itemprogress).setVisibility(View.GONE);
                          Toast.makeText(Registration.this,"No data Found",Toast.LENGTH_LONG).show();
                      }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void registerUser() {

        String rclient=clientname.getText().toString();
        String rusername=clusername.getText().toString();

        //String rstatus=clientstatus.getText().toString();

        if(TextUtils.isEmpty(rclient) || TextUtils.isEmpty(rusername) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(finalstatus) ){

            Toast.makeText(Registration.this,"One or more fields are Empty",Toast.LENGTH_LONG).show();

        }
        else{



            ApiInterface apiInterface=ApiClient.getApiClient().create(ApiInterface.class);
            Call<SignResult> result=apiInterface.getMessageInfo(rclient,rusername,pass,finalstatus);
            result.enqueue(new Callback<SignResult>() {
                @Override
                public void onResponse(Call<SignResult> call, Response<SignResult> response) {
                  String messageresult= response.body().getMessage();
                  if(messageresult.equals("success")){

                      Toast.makeText(Registration.this," You Registered Successfully",Toast.LENGTH_LONG).show();
                      clients.clear();
                      jSonParsing();
                  }
                  else {

                      Toast.makeText(Registration.this,""+messageresult,Toast.LENGTH_LONG).show();
                  }
                }

                @Override
                public void onFailure(Call<SignResult> call, Throwable t) {

                }
            });





        }
    }

    private void initializeAll() {

           clientname=findViewById(R.id.rcname);
           clusername=findViewById(R.id.rcuser);
           clientpass=findViewById(R.id.rcpass);
        spinner_sttus=findViewById(R.id.spinner_status);
        spinner_sttus.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);
        ArrayAdapter aa7 = new ArrayAdapter(Registration.this,android.R.layout.simple_spinner_item, status);
        aa7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//Setting the ArrayAdapter data on the Spinner
        spinner_sttus.setAdapter(aa7);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


        switch (adapterView.getId()) {
            case R.id.spinner_status:
                finalstatus = spinner_sttus.getSelectedItem().toString();
                Toast.makeText(Registration.this,"elements"+finalstatus,Toast.LENGTH_LONG).show();
                // Toast.makeText(Main2Activity.this, "Selected Prefession:" + _jobpreference, Toast.LENGTH_LONG).show();
                break;


        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
