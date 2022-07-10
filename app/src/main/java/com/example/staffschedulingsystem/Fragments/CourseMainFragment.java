package com.example.staffschedulingsystem.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.staffschedulingsystem.Adapters.AdminCourseAdapter;
import com.example.staffschedulingsystem.Teacher.AddStaff;
import com.example.staffschedulingsystem.Models.Courses;
import com.example.staffschedulingsystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class CourseMainFragment extends Fragment {

    private RecyclerView recyclerCourses;
    private DatabaseReference retreive;
    private AdminCourseAdapter adapter;
    private ArrayList<Courses> mList;
    private TextInputLayout courseName,courseCode,courseFaculty;
    private android.app.AlertDialog alertDialog;
    private String coursename,coursecode,coursefaculty;
    private ProgressDialog dialog;
    private long pressedTime;
    private Button btnAddCourse;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_course, container, false);
        btnAddCourse = view.findViewById(R.id.btnAddCourse);
        courseCode = view.findViewById(R.id.editcourseCode);
        courseName = view.findViewById(R.id.courseName);
        courseFaculty = view.findViewById(R.id.editcourseFaculty);
        recyclerCourses = view.findViewById(R.id.recycler_courses);
        recyclerCourses.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerCourses.setHasFixedSize(true);

        mList = new ArrayList<>();
        adapter = new AdminCourseAdapter(mList, getContext());
        recyclerCourses.setAdapter(adapter);

        retreive = FirebaseDatabase.getInstance().getReference("Course");
        retreive.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                mList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Courses course = dataSnapshot.getValue(Courses.class);
                    mList.add(course);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        dialog = new ProgressDialog(getContext());
        btnAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerCourse();
            }
        });

      return view;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.action_search:

                return true;
            case  R.id.action_view_subjects:
                startActivity(new Intent(getContext(), AddStaff.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private boolean validateFaculty()
    {
        coursefaculty = courseFaculty.getEditText().getText().toString().trim().toUpperCase();
        if(coursefaculty.isEmpty())
        {
            courseFaculty.setError("filed must not be empty");
            courseFaculty.requestFocus();
            return false;
        }
        else
        {
            courseFaculty.setError(null);
            courseFaculty.requestFocus();
            return true;
        }
    }
    private boolean validateCode()
    {
        coursename = courseName.getEditText().getText().toString().trim().toUpperCase();
        if(coursename.isEmpty())
        {
            courseCode.setError("filed must not be empty");
            courseCode.requestFocus();
            return false;
        }
        else
        {
            courseCode.setError(null);
            courseCode.requestFocus();
            return true;
        }
    }
    private boolean validateName()
    {
        coursecode =courseCode.getEditText().getText().toString().trim().toUpperCase();
        if(coursecode.isEmpty())
        {
            courseName.setError("filed must not be empty");
            courseName.requestFocus();
            return false;
        }
        else
        {
            courseName.setError(null);
            courseName.requestFocus();
            return true;
        }
    }
    private void registerCourse()
    {
        if(!validateName() | !validateCode() | !validateFaculty())
        {
            return;
        }
        else
        {
            dialog.setTitle("Register Course");
            dialog.setMessage("Please wait while adding your course.......");
            dialog.show();

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Course").push();
            String courseID=reference.getKey();
            final HashMap<String,Object> hashMap= new HashMap<>();

            hashMap.put("courseID",courseID);
            hashMap.put("courseName",coursename);
            hashMap.put("courseCode",coursecode);
            hashMap.put("courseFaculty",coursefaculty);

            reference.updateChildren(hashMap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(getContext(), "Course Registered successfully", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                                else
                                {
                                    Toast.makeText(getContext(), "Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }

                            }
                            else
                            {
                                Toast.makeText(getContext(), "Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }
                    });
        }
    }


}