package com.example.staffschedulingsystem.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.staffschedulingsystem.Models.ScheduledSubjects;
import com.example.staffschedulingsystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

public class ScheduledStaffAdapter extends RecyclerView.Adapter<ScheduledStaffAdapter.viewHolder>
{

    private ArrayList<ScheduledSubjects> Items;
    private Context context;

    public ScheduledStaffAdapter(ArrayList<ScheduledSubjects> items, Context context) {
        Items = items;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_single_scheduled_subject,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull viewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.subject_vanue.setText(Items.get(position).getVenue());
        holder.subjectstartime.setText(Items.get(position).getStartTime());
        holder.subjectendtime.setText(Items.get(position).getEndTime());
        holder.subject_day.setText(Items.get(position).getDay());
        holder.subject_name.setText(Items.get(position).getSubjectName());
        holder.deleteCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Schedules");
                reference.child(Items.get(position).getSubjectID())
                        .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                      if(task.isSuccessful())
                      {
                          Toast.makeText(context, "scheduled subject deleted successfully", Toast.LENGTH_SHORT).show();
                      }
                      else
                      {
                          Toast.makeText(context, "Error occure: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                      }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return Items.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        public TextView subject_name, subject_day, subject_vanue, subjectstartime, subjectendtime;

        public Button deleteCourse;
        public viewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            subject_vanue = itemView.findViewById(R.id.subject_vanue);
            subjectstartime = itemView.findViewById(R.id.subjectstartime);
            subjectendtime = itemView.findViewById(R.id.subjectendtime);
            subject_day = itemView.findViewById(R.id.subject_day);
            subject_name =itemView.findViewById(R.id.subject_name);

            deleteCourse =itemView.findViewById(R.id.deleteCourse);

        }
    }
}
