package com.example.staffschedulingsystem.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.staffschedulingsystem.Adapters.StudentAdapter;
import com.example.staffschedulingsystem.Adapters.userAdapter;
import com.example.staffschedulingsystem.Admin.MainActivity;
import com.example.staffschedulingsystem.Models.StudentModel;
import com.example.staffschedulingsystem.Models.UsersModel;
import com.example.staffschedulingsystem.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class StudentMainFragment extends Fragment {

    private Query retrieve;
    private RecyclerView recyclerView;
    private StudentAdapter adapter;
    private ArrayList<StudentModel> mList;
    private ImageView btn_student_back;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.activity_main_student, container, false);

        recyclerView = view.findViewById(R.id.recyclerview_view_students);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        mList = new ArrayList<>();
        adapter = new StudentAdapter(mList, getContext());
        recyclerView.setAdapter(adapter);
        retrieve = FirebaseDatabase.getInstance().getReference("Users")
                .orderByChild("userType")
                .equalTo(2);
        retrieve.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot)
            {
                mList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    StudentModel course = dataSnapshot.getValue(StudentModel.class);
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