package com.example.staffschedulingsystem.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.staffschedulingsystem.Teacher.ViewStaffSchedule;
import com.example.staffschedulingsystem.Models.UsersModel;
import com.example.staffschedulingsystem.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class userAdapter extends RecyclerView.Adapter<userAdapter.viewHolder>
{
    private ArrayList<UsersModel> Items;
    private Context context;

    public userAdapter(ArrayList<UsersModel> items, Context context) {
        Items = items;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_staff,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull viewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(Items.get(position).getName());
        holder.email.setText(Items.get(position).getEmail());
        holder.contact.setText(Items.get(position).getPhone());
        holder.btnViewSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context, ViewStaffSchedule.class);
                intent.putExtra("staffID",Items.get(position).getUserID());
                v.getContext().startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return Items.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        public TextView btnViewSchedule,name,email,contact;
        public Button btnRegister;
        public RelativeLayout layout;
        public viewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
           email = itemView.findViewById(R.id.email);
            contact = itemView.findViewById(R.id.phone);
            btnViewSchedule = itemView.findViewById(R.id.btnViewSchedule);
        }
    }
}
