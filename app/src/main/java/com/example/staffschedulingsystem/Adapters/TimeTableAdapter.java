package com.example.staffschedulingsystem.Adapters;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.staffschedulingsystem.Models.TimeTable;
import com.example.staffschedulingsystem.R;
import com.example.staffschedulingsystem.Admin.ScheduleStaff;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class TimeTableAdapter extends RecyclerView.Adapter<TimeTableAdapter.ViewHolder> {
    private ArrayList<TimeTable> Items;
    private Context context;


    public TimeTableAdapter(ArrayList<TimeTable> items, Context context) {
        Items = items;
        this.context = context;
    }
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_time_table,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.SubjectName.setText(Items.get(position).getSubjName());
        holder.SubjectVanue.setText(Items.get(position).getSubjVanue());
        holder.SubjectDay.setText(Items.get(position).getSubjDay());
        holder.SubjectTime.setText(Items.get(position).getStartTime()+" - "+ Items.get(position).getEndTime());
        holder.btnScheduleStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ScheduleStaff.class);

                intent.putExtra("subjectName",Items.get(position).getSubjName());
                intent.putExtra("subjectID",Items.get(position).getSubjID());
                intent.putExtra("day",Items.get(position).getSubjDay());
                intent.putExtra("startTime",Items.get(position).getStartTime());
                intent.putExtra("endTime",Items.get(position).getEndTime());
                intent.putExtra("venue",Items.get(position).getSubjVanue());

                v.getContext().startActivity(intent);
            }
        });


    }
    @Override
    public int getItemCount() {
        return Items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView SubjectName,SubjectVanue,SubjectTime,SubjectDay;
        public ImageView userimage;
        public Button btnScheduleStaff;
        public RelativeLayout layout;
        public ViewHolder(@NonNull @NotNull View itemView)
        {
            super(itemView);
            SubjectName = itemView.findViewById(R.id.tvSubjectName);
            SubjectVanue = itemView.findViewById(R.id.tvSubjectVanue);
            SubjectTime = itemView.findViewById(R.id.tvSubjectTime);
            //userimage = itemView.findViewById(R.id.accnt_image);
            layout = itemView.findViewById(R.id.ttlayout);
            SubjectDay = itemView.findViewById(R.id.SubjectDay);
            btnScheduleStaff = itemView.findViewById(R.id.btnScheduleStaff);
        }
    }

}
