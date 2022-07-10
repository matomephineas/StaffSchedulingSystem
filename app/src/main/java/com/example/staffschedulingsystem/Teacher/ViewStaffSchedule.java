package com.example.staffschedulingsystem.Teacher;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.staffschedulingsystem.Adapters.ScheduledStaffAdapter;
import com.example.staffschedulingsystem.Models.ScheduledSubjects;
import com.example.staffschedulingsystem.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ViewStaffSchedule extends AppCompatActivity {

    private RecyclerView recyclerCourses;
    private ScheduledStaffAdapter adapter;
    private ArrayList<ScheduledSubjects> mList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_staff_schedules);
        recyclerCourses=findViewById(R.id.sheduled_recyclerview);

        Bundle bundle = getIntent().getExtras();
        String staffID = bundle.getString("staffID");

        recyclerCourses.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerCourses.setHasFixedSize(true);

        //get the data from previous page

        mList = new ArrayList<>();
        adapter = new ScheduledStaffAdapter(mList,getApplicationContext());
        recyclerCourses.setAdapter(adapter);

       Query retrieve = FirebaseDatabase.getInstance().getReference("Schedules")
               .orderByChild("staffID")
                .equalTo(staffID);
        retrieve.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                mList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    ScheduledSubjects course = dataSnapshot.getValue(ScheduledSubjects.class);
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