package com.example.staffschedulingsystem.Teacher;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.staffschedulingsystem.Adapters.ManageSubjectAdapter;
import com.example.staffschedulingsystem.Adapters.TeachingSheduledSubjectAdapter;
import com.example.staffschedulingsystem.Models.ScheduledSubjects;
import com.example.staffschedulingsystem.Models.Subject;
import com.example.staffschedulingsystem.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TeachingSubjectsFragment extends Fragment {

    private RecyclerView recyclerCourses;
    private String course_name,course_code,choice, userId;
    private TeachingSheduledSubjectAdapter adapter;
    private ArrayList<ScheduledSubjects> mList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_teaching_subjects, container, false);
        recyclerCourses=view.findViewById(R.id.viewTeachingSubjects);
        recyclerCourses.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerCourses.setHasFixedSize(true);

        Bundle bundle =  getActivity().getIntent().getExtras();
        userId = bundle.getString("userId");

        mList = new ArrayList<>();
        adapter = new TeachingSheduledSubjectAdapter(mList,getContext());
        recyclerCourses.setAdapter(adapter);

        Query retrieve = FirebaseDatabase.getInstance().getReference("Schedules")
                .orderByChild("staffID")
                .equalTo(userId);
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
        return view;
    }
}