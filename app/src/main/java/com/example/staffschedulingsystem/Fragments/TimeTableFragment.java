package com.example.staffschedulingsystem.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.staffschedulingsystem.Adapters.TimeTableAdapter;
import com.example.staffschedulingsystem.Models.TimeTable;
import com.example.staffschedulingsystem.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TimeTableFragment extends Fragment {

    private RecyclerView recyclerView;
    private DatabaseReference retrieve;
    private TimeTableAdapter adapter;
    private ArrayList<TimeTable> mList;
    ImageView report,generateReport;
    String day;
    int hours;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_timetable, container, false);

        Date date = new Date();
        Calendar c = Calendar.getInstance();
        hours = c.get(Calendar.HOUR_OF_DAY);
        day = new SimpleDateFormat("EEEE").format(date);

        recyclerView =view.findViewById(R.id.timetable);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        mList = new ArrayList<>();
        adapter = new TimeTableAdapter(mList,getContext());
        recyclerView.setAdapter(adapter);

        retrieve = FirebaseDatabase.getInstance().getReference("TimeTable");
        retrieve.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @com.google.firebase.database.annotations.NotNull DataSnapshot snapshot) {
                mList.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    //it gets the data from the Time table class and puts them in an adapter
                    TimeTable timeTable = dataSnapshot.getValue(TimeTable.class);
                    mList.add(timeTable);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull @com.google.firebase.database.annotations.NotNull DatabaseError error) {
            }
        });


        return view;
    }
}