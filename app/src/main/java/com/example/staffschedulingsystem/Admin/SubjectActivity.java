package com.example.staffschedulingsystem.Admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.Toast;

import com.example.staffschedulingsystem.Adapters.ManageSubjectAdapter;
import com.example.staffschedulingsystem.Models.Subject;
import com.example.staffschedulingsystem.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class SubjectActivity extends AppCompatActivity {

    private RecyclerView recyclerCourses;
    private String course_name,course_code,choice,courseID;
    private ManageSubjectAdapter adapter;
    private ArrayList<Subject> mList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        recyclerCourses=findViewById(R.id.recycler_manage_modules);
        recyclerCourses.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerCourses.setHasFixedSize(true);

        Bundle bundle =  getIntent().getExtras();
        courseID = bundle.getString("courseID");

        mList = new ArrayList<>();
        adapter = new ManageSubjectAdapter(mList,getApplicationContext());
        recyclerCourses.setAdapter(adapter);

       Query retrieve = FirebaseDatabase.getInstance().getReference("CourseModules")
               .orderByChild("courseID")
               .equalTo(courseID);
        retrieve.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                mList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                        Subject course = dataSnapshot.getValue(Subject.class);
                        mList.add(course);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

}