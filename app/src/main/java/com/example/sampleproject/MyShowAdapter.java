package com.example.sampleproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyShowAdapter extends RecyclerView.Adapter<MyShowAdapter.ItemViewHolder> {

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    ArrayList<Client> clients;

    public MyShowAdapter(ArrayList<Client> clients, Context context) {
    this.clients=clients;
        this.context = context;
    }

    Context context;
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(context).inflate(R.layout.useritems,parent,false);
       return new ItemViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

          Client client=clients.get(position);
          holder.scid.setText(client.getCid());
          holder.showcname.setText(client.getcNm());
          holder.showuname.setText(client.getuNm());


    }

    @Override
    public int getItemCount() {
      return   clients.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView scid,showcname,showuname;
        public ItemViewHolder(@NonNull View itemView,final OnItemClickListener listener) {
            super(itemView);

            scid=itemView.findViewById(R.id.showcid);
            showcname=itemView.findViewById(R.id.showcname);
            showuname=itemView.findViewById(R.id.showuname);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

        }
    }
}
