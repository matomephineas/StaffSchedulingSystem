package com.example.staffschedulingsystem.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.staffschedulingsystem.Models.StudentModel;
import com.example.staffschedulingsystem.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.viewHolder>
{
    private ArrayList<StudentModel> Items;
    private Context context;

    public StudentAdapter(ArrayList<StudentModel> items, Context context) {
        Items = items;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_student,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull viewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(Items.get(position).getName());
        holder.email.setText(Items.get(position).getEmail());
        holder.department.setText(Items.get(position).getDepartment());
        holder.course.setText(Items.get(position).getCourseName());

    }
    @Override
    public int getItemCount() {
        return Items.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        public TextView department,name,email,course;
        public Button btnRegister;
        public RelativeLayout layout;
        public viewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
           email = itemView.findViewById(R.id.email);

            department = itemView.findViewById(R.id.department);
            course =itemView.findViewById(R.id.course);
        }
    }
}
