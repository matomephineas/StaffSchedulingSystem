package com.example.staffschedulingsystem.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.staffschedulingsystem.Models.MarkedRegister;
import com.example.staffschedulingsystem.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class StudentMarkedRegisterAdapter extends RecyclerView.Adapter<StudentMarkedRegisterAdapter.ViewHolder> {
    private ArrayList<MarkedRegister> Items;
    private Context context;

    public StudentMarkedRegisterAdapter(ArrayList<MarkedRegister> items, Context context) {
        Items = items;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.marked_register,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.SubjectName.setText(Items.get(position).getSubjectName());
       // holder.name.setText(Items.get(position).getStudentName());
        holder.tvDate.setText(Items.get(position).getDateMarked());
        holder.tvTime.setText(Items.get(position).getTimeMarked());
        holder.tvStatus.setText(Items.get(position).getStatus());
        holder.tvStudentName.setText(Items.get(position).getStudentName());
        holder.tvLocation.setText(Items.get(position).getAddress());


    }
    @Override
    public int getItemCount() {
        return Items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView SubjectName, name,tvStatus,Day, tvTime, tvDate,tvStudentName,tvLocation;

        public ViewHolder(@NonNull @NotNull View itemView)
        {
            super(itemView);
            SubjectName = itemView.findViewById(R.id.tvName);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvStudentName = itemView.findViewById(R.id.tvStudentName);

        }
    }


}
