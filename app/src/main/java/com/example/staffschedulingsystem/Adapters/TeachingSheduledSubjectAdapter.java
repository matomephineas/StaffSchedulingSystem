package com.example.staffschedulingsystem.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.staffschedulingsystem.Models.ScheduledSubjects;
import com.example.staffschedulingsystem.Models.TeacherSubjectModels;
import com.example.staffschedulingsystem.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TeachingSheduledSubjectAdapter extends RecyclerView.Adapter<TeachingSheduledSubjectAdapter.ManageSubjectViewHolder>
{
    private ArrayList<ScheduledSubjects> Items;
    private Context context;

    public TeachingSheduledSubjectAdapter(ArrayList<ScheduledSubjects> items, Context context) {
        Items = items;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ManageSubjectViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_teacher_scheduled_subject,parent,false);
        return new ManageSubjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ManageSubjectViewHolder holder, @SuppressLint("RecyclerView") int position)
    {
        holder.SubjectScheduledName.setText(Items.get(position).getSubjectName());
        holder.SubjectScheduledDepartment.setText(Items.get(position).getDepartment());
        holder.SubjectScheduledDay.setText(Items.get(position).getDay());
        holder.SubjectScheduledVenue.setText(Items.get(position).getVenue());
        holder.SubjectScheduledTime.setText(Items.get(position).getStartTime() +" - " + Items.get(position).getEndTime());
    }

    @Override
    public int getItemCount() {
        return Items.size();
    }

    public static class ManageSubjectViewHolder extends RecyclerView.ViewHolder
    {
        public TextView SubjectScheduledName, SubjectScheduledDepartment, SubjectScheduledVenue, SubjectScheduledDay,SubjectScheduledTime;

        public ManageSubjectViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            SubjectScheduledName = itemView.findViewById(R.id.SubjectScheduledName);
            SubjectScheduledDepartment = itemView.findViewById(R.id.SubjectScheduledDepartment);
            SubjectScheduledVenue = itemView.findViewById(R.id.SubjectScheduledVenue);
            SubjectScheduledDay = itemView.findViewById(R.id.SubjectScheduledDay);
            SubjectScheduledTime = itemView.findViewById(R.id.SubjectScheduledTime);
        }
    }
}
