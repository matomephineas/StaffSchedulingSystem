package com.example.staffschedulingsystem;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.staffschedulingsystem.Adapters.StudentMarkedRegisterAdapter;
import com.example.staffschedulingsystem.Models.MarkedRegister;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class StudentMarkedAttendanceFragment extends Fragment {

    private RecyclerView recyclerView;
    private DatabaseReference retrieve;
    private StudentMarkedRegisterAdapter adapter;
    private ArrayList<MarkedRegister> mList;
    private String userId;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_student_marked_attendance, container, false);
        recyclerView = view.findViewById(R.id.markedregisterrecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        Bundle bundle = getActivity().getIntent().getExtras();

        mList = new ArrayList<>();
        adapter = new StudentMarkedRegisterAdapter(mList,getContext());
        recyclerView.setAdapter(adapter);

        Query retrieve = FirebaseDatabase.getInstance().getReference("Register Marked");

        retrieve.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @com.google.firebase.database.annotations.NotNull DataSnapshot snapshot) {
                mList.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    //it gets the data from the Time table class and puts them in an adapter
                    MarkedRegister timeTable = dataSnapshot.getValue(MarkedRegister.class);
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