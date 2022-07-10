package com.example.staffschedulingsystem.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.staffschedulingsystem.Admin.EditCourse;
import com.example.staffschedulingsystem.Admin.EditSubject;
import com.example.staffschedulingsystem.Models.Subject;
import com.example.staffschedulingsystem.R;
import com.example.staffschedulingsystem.Admin.WeeklyDaysActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ManageSubjectAdapter extends RecyclerView.Adapter<ManageSubjectAdapter.ManageSubjectViewHolder>
{
    private ArrayList<Subject> Items;
    private Context context;

    public ManageSubjectAdapter(ArrayList<Subject> items, Context context) {
        Items = items;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ManageSubjectViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_admin_manage_subjects,parent,false);
        return new ManageSubjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ManageSubjectViewHolder holder, @SuppressLint("RecyclerView") int position)
    {
        holder. admin_view_subject_name.setText(Items.get(position).getSubjectName());
        holder.admin_view_subject_code.setText(Items.get(position).getSubjectCode());
        holder. admin_view_qual_name.setText(Items.get(position).getCourseFaculty());
        holder.admin_view_qual_code.setText(Items.get(position).getCourseCode());

        holder.addTimetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courseID=Items.get(position).getCourseID();
                String courseCode =Items.get(position).getCourseCode();
                String subjectID=Items.get(position).getSubjectID();
                String subjectName =Items.get(position).getSubjectName();
                String subjectCode =Items.get(position).getSubjectCode();


                Intent intent = new Intent(context, WeeklyDaysActivity.class);
                intent.putExtra("courseID",courseID);
                intent.putExtra("courseCode",courseCode);
                intent.putExtra("subjectID",subjectID);
                intent.putExtra("subjectName",subjectName);
                intent.putExtra("subjectCode",subjectCode);
                v.getContext().startActivity(intent);


            }
        });

        holder.editSubject.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(context, EditSubject.class);
                intent.putExtra("subjectName",Items.get(position).getSubjectName());
                intent.putExtra("subjectCode",Items.get(position).getSubjectCode());
                intent.putExtra("subjectID",Items.get(position).getSubjectID());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        holder.deleteSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference reference= FirebaseDatabase.getInstance().getReference("CourseModules");
                reference.child(Items.get(position).getSubjectID())
                        .removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(context, "Subject removed successfully", Toast.LENGTH_SHORT).show();

                                }
                                else
                                {
                                    Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
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

    public static class ManageSubjectViewHolder extends RecyclerView.ViewHolder
    {
        public TextView admin_view_subject_name,admin_view_subject_code,admin_view_qual_name,admin_view_qual_code;
        public TextView addTimetable;
        public ImageView editSubject,deleteSubject;
        public ManageSubjectViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            admin_view_subject_name = itemView.findViewById(R.id.admin_view_subject_name);
            admin_view_subject_code= itemView.findViewById(R.id.admin_view_subject_code);
            admin_view_qual_name= itemView.findViewById(R.id.admin_view_qual_name);
            admin_view_qual_code= itemView.findViewById(R.id.admin_view_qual_code);
            addTimetable = itemView.findViewById(R.id.addTimetable);
            editSubject = itemView.findViewById(R.id.editSubject);
            deleteSubject = itemView.findViewById(R.id.deleteSubject);
        }
    }
}
