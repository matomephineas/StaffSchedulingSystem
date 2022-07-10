package com.example.staffschedulingsystem.Teacher;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.staffschedulingsystem.Adapters.userAdapter;
import com.example.staffschedulingsystem.Models.UsersModel;
import com.example.staffschedulingsystem.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class ViewStaff extends Fragment {
    private Query retrieve;
    private RecyclerView recyclerView;
    private userAdapter adapter;
    private ArrayList<UsersModel> mList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_view_staff, container, false);
        recyclerView = view.findViewById(R.id.recycler_manage_staff);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        mList = new ArrayList<>();
        adapter = new userAdapter(mList, getContext());
        recyclerView.setAdapter(adapter);
        retrieve = FirebaseDatabase.getInstance().getReference("Users")
                .orderByChild("userType")
                .equalTo(1);
        retrieve.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot)
            {
                mList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    UsersModel course = dataSnapshot.getValue(UsersModel.class);
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