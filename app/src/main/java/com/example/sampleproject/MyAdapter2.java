package com.example.sampleproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.ExampleViewHolder2> {

    ArrayList<Details> details2;
    Context context;

    public MyAdapter2(ArrayList<Details> details2, Context context) {
        this.details2 = details2;
        this.context = context;
    }


    @NonNull
    @Override
    public ExampleViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.item2,parent,false);
       return new ExampleViewHolder2(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder2 holder, int position) {

        Details currentobj=details2.get(position);
        holder.description2.setText(currentobj.getNameEn());

        String dates=currentobj.getDate();
        String a[]=  dates.split("-");
        String newdate=a[2]+"-"+a[1]+"-"+a[0];
        holder.date2.setText(newdate);
        holder.amount2.setText(currentobj.getAmount());

    }

    @Override
    public int getItemCount() {
        return details2.size();
    }

    public class ExampleViewHolder2 extends RecyclerView.ViewHolder{

        TextView description2,date2,amount2;
        public ExampleViewHolder2(@NonNull View itemView) {
            super(itemView);

            description2=itemView.findViewById(R.id.groupname2);
            date2=itemView.findViewById(R.id.date2);
            amount2=itemView.findViewById(R.id.amount2);
        }
    }
}
