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

import com.example.staffschedulingsystem.Admin.ActivityAddStudent;
import com.example.staffschedulingsystem.Admin.AddSubject;
import com.example.staffschedulingsystem.Admin.EditCourse;
import com.example.staffschedulingsystem.Admin.SubjectActivity;
import com.example.staffschedulingsystem.Models.Courses;
import com.example.staffschedulingsystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

public class AdminCourseAdapter extends RecyclerView.Adapter<AdminCourseAdapter.viewHolder>
{

    private ArrayList<Courses> Items;
    private Context context;
    public AdminCourseAdapter(ArrayList<Courses> items, Context context) {
        Items = items;
        this.context = context;
    }
    @NonNull
    @NotNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_available_courses,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull viewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.coureName.setText("Name: "+Items.get(position).getCourseName());
        holder.courseCode.setText("Code: "+Items.get(position).getCourseCode());
        holder.courseFaculty.setText("Faculty: "+Items.get(position).getCourseFaculty());
        holder.addStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivityAddStudent.class);
                intent.putExtra("courseName",Items.get(position).getCourseName());
                intent.putExtra("courseID",Items.get(position).getCourseID());
                intent.putExtra("faculty",Items.get(position).getCourseFaculty());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        holder.deleteCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Course");
                reference.child(Items.get(position).courseID)
                        .removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                               if(task.isSuccessful())
                               {
                                   Toast.makeText(context, "Course removed successfully", Toast.LENGTH_SHORT).show();

                               }
                               else
                               {
                                   Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                               }
                            }
                        });
            }
        });
        holder.viewSubjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SubjectActivity.class);
                intent.putExtra("courseName",Items.get(position).getCourseName());
                intent.putExtra("courseID",Items.get(position).getCourseID());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        holder.editCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
               Intent intent = new Intent(context, EditCourse.class);
                intent.putExtra("courseName",Items.get(position).getCourseName());
                intent.putExtra("courseCode",Items.get(position).getCourseCode());
                intent.putExtra("courseID",Items.get(position).getCourseID());
                intent.putExtra("courseFaculty",Items.get(position).getCourseFaculty());
               context.startActivity(intent);
            }
        });
        holder.addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddSubject.class);
                intent.putExtra("courseName",Items.get(position).getCourseName());
                intent.putExtra("courseCode",Items.get(position).getCourseCode());
                intent.putExtra("courseID",Items.get(position).getCourseID());
                intent.putExtra("courseFaculty",Items.get(position).getCourseFaculty());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Items.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        public TextView viewSubjects,addStudent,addCourse,coureName,courseCode,courseFaculty;

        public ImageView editCourse,deleteCourse;
        public viewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            coureName = itemView.findViewById(R.id.view_course_name);
            courseCode = itemView.findViewById(R.id.view_course_code);
            courseFaculty = itemView.findViewById(R.id.view_course_faculty);
            addCourse = itemView.findViewById(R.id.addCourse);
            editCourse =itemView.findViewById(R.id.editCourse);
            deleteCourse =itemView.findViewById(R.id.deleteCourse);
            viewSubjects=itemView.findViewById(R.id.viewSubjects);
            addStudent=itemView.findViewById(R.id.addStudent);
        }
    }
}
