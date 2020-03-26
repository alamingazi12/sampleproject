package com.example.sampleproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ExampleViewHolder> {

    ArrayList <Details> details1;

   Context context;

    public MyAdapter(ArrayList<Details> details1, Context context) {
        this.details1 = details1;
        this.context = context;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new ExampleViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {

           Details currentobj=details1.get(position);
           holder.description.setText(currentobj.getNameEn());

           String dates=currentobj.getDate();
           String a[]=  dates.split("-");
           String newdate=a[2]+"-"+a[1]+"-"+a[0];
           holder.date.setText(newdate);
           holder.amount.setText(currentobj.getAmount());


    }

    @Override
    public int getItemCount() {
        return details1.size();
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder{

        TextView description,date,amount;
        Button showdetails;
        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);

              description=itemView.findViewById(R.id.groupname);
              date=itemView.findViewById(R.id.date);
              amount=itemView.findViewById(R.id.amount);


        }
    }



}
