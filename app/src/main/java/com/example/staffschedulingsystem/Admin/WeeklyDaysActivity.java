package com.example.staffschedulingsystem.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.staffschedulingsystem.Adapters.WeekAdapter;
import com.example.staffschedulingsystem.R;

import java.util.ArrayList;
import java.util.Arrays;

public class WeeklyDaysActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    WeekAdapter adapter;
    // Using ArrayList to store images data
    ArrayList weekImages = new ArrayList<>(Arrays.asList(R.drawable.monday, R.drawable.tuesday, R.drawable.wednesday, R.drawable.thursday
            , R.drawable.friday, R.drawable.saturday));
    ArrayList weekNames = new ArrayList<>(Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday","Saturday"));
    String courseID,courseCode,subjectID,subjectName,subjectCode;
    private TextView subject_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_days);
        recyclerView = findViewById(R.id.recyclerview);
        subject_name = findViewById(R.id.subject_name);
        Bundle bundle = getIntent().getExtras();
        //get the data from previous page
        courseID = bundle.getString("courseID");
        courseCode= bundle.getString("courseCode");
        subjectID= bundle.getString("subjectID");
        subjectName = bundle.getString("subjectName");
        subjectCode= bundle.getString("subjectCode");

        subject_name.setText(subjectName);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        // Sending reference and data to Adapter
       adapter = new WeekAdapter( this, weekNames,weekImages,courseID,courseCode,subjectID,subjectName,subjectCode);

        // Setting Adapter to RecyclerView
        recyclerView.setAdapter(adapter);
    }
}