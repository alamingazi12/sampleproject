package com.example.sampleproject;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.TypefaceSpan;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.sampleproject.R.id.actionbar_text;

public class MainActivity extends AppCompatActivity {
   ConnectivityManager connectivityManager;
   String cid;
    User user;
    String date;
    Button calenderbtn;
    DatePicker datePicker;
    TextView group1,group2;
  ArrayList<Details> details1;
    ArrayList<Details> details2;
    RecyclerView recyclerView,recyclerView2;
    ArrayList <Group> groups;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



       BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
       bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem item) {

               switch (item.getItemId()) {
                   case R.id.nav_home:

                       break;
                   case R.id.nav_favorites:

                       break;
                   case R.id.nav_search:

                       break;
               }
               return true;
           }
       });


         Bundle bundle= getIntent().getExtras();
           user=bundle.getParcelable("obj");
           cid=user.getCid();



       // Toast.makeText(MainActivity.this,"status is:"+user.getuSt(),Toast.LENGTH_LONG).show();





        datePicker=findViewById(R.id.datepicker);
        Calendar calendar=Calendar.getInstance();
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int day) {
               String years= String.valueOf(year);
               int monthss=month+1;
               String months= String.valueOf((monthss));
                if(months.length()<2){

                    months="0"+months;
                }
                String days=String.valueOf(day);
                if(days.length()<2){


                    days="0"+days;
                }


              date=years+"-"+months+"-"+days;

                firstInitialize();
                secondInitialize();

            }
        });




        group1=findViewById(R.id.groupname);
        group2=findViewById(R.id.groupname2);



        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView2=findViewById(R.id.recyclerview2);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));



        firstInitialize();
        secondInitialize();


    }



    private void secondInitialize() {

        Call<InfoList> infoList;

        ApiInterface apiInterface= ApiClient.getApiClient().create(ApiInterface.class);

        if(date!=null){

            infoList= apiInterface.getInfoList2(date,cid);

        }else {

            Date today = new Date(); // Fri Jun 17 14:54:28 PDT 2016
            // don't forget this if date is arbitrary e.g. 01-01-2014

            SimpleDateFormat simpleDateFormats=new SimpleDateFormat("yyyy-MM-dd");
            String newdate= simpleDateFormats.format(today);
            Toast.makeText(MainActivity.this,"value cid:"+cid,Toast.LENGTH_LONG).show();
            infoList= apiInterface.getInfoList2(newdate,cid);
        }


        infoList.enqueue(new Callback<InfoList>() {
            @Override
            public void onResponse(Call<InfoList> call, Response<InfoList> response) {
                details2= response.body().getDetails();
                if(!details2.isEmpty()){

                    group2.setText(details2.get(0).getName());
                    MyAdapter2 myAdapter=new MyAdapter2(details2,MainActivity.this);

                    findViewById(R.id.progress2).setVisibility(View.GONE);

                    recyclerView2.setAdapter(myAdapter);

                }else {

                    Toast.makeText(MainActivity.this," "+response.body().getMessage(),Toast.LENGTH_LONG).show();
                    findViewById(R.id.progress2).setVisibility(View.GONE);

                }



            }

            @Override
            public void onFailure(Call<InfoList> call, Throwable t) {

            }
        });
    }

    private void firstInitialize() {
        Call<InfoList> infoList;

       ApiInterface apiInterface= ApiClient.getApiClient().create(ApiInterface.class);

        if(date!=null){
            infoList= apiInterface.getInfoList(date,cid);

        }else {

            Date today = new Date(); // Fri Jun 17 14:54:28 PDT 2016
            // don't forget this if date is arbitrary e.g. 01-01-2014

            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
            String newdate= simpleDateFormat.format(today);

            infoList= apiInterface.getInfoList(newdate,cid);
        }
       // Call<InfoList> infoList= apiInterface.getInfoList("2019-09-01");

        infoList.enqueue(new Callback<InfoList>() {
            @Override
            public void onResponse(Call<InfoList> call, Response<InfoList> response) {
               details1= response.body().getDetails();
               if(!details1.isEmpty()){


                   Details details=details1.get(0);

                   group1.setText(details.getName());
                   MyAdapter myAdapter=new MyAdapter(details1,MainActivity.this);

                   findViewById(R.id.progress1).setVisibility(View.GONE);

                   recyclerView.setAdapter(myAdapter);

                }else {

                   Toast.makeText(MainActivity.this,""+response.body().getMessage(),Toast.LENGTH_LONG).show();
                   findViewById(R.id.progress1).setVisibility(View.GONE);

               }


            }

            @Override
            public void onFailure(Call<InfoList> call, Throwable t) {

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.optionmenu, menu);

        if(user.getuSt().equals("0")){

            menu.getItem(0).setVisible(false);
           //Toast.makeText(MainActivity.this,"title:"+menu.getItem(0).getTitle(),Toast.LENGTH_LONG).show()  ;


        }





        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.signup:
                Intent intent=new Intent(MainActivity.this,Registration.class);
                startActivity(intent);
                return true;
            case R.id.changepass:
                Intent intent2=new Intent(MainActivity.this,PasswordActivity.class);
                Bundle bundle=new Bundle();
                bundle.putParcelable("user",user);
                intent2.putExtras(bundle);

                startActivity(intent2);
                return true;


            default:
                return true;
                //return super.onContextItemSelected(item);
        }
    }


}
