package com.example.staffschedulingsystem.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.staffschedulingsystem.Models.UsersModel;
import com.example.staffschedulingsystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class ScheduleStaff extends AppCompatActivity {
    private Query retrieve;
    private ScheduleStaffAdapter adapter;
    private ArrayList<UsersModel> mList;
    String day, startTime,subjectID,subjectName, endTime,venue;
    private TextView textSubjectName,textStartTime,textEndTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_staff);

        Bundle bundle = getIntent().getExtras();
        day = bundle.getString("day");
        startTime = bundle.getString("startTime");
        subjectID= bundle.getString("subjectID");
        subjectName = bundle.getString("subjectName");
        endTime = bundle.getString("endTime");
        venue = bundle.getString("venue");

        RecyclerView recyclerView = findViewById(R.id.lectures);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        mList = new ArrayList<>();
        adapter = new ScheduleStaffAdapter(mList, this);
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
    }

    public class ScheduleStaffAdapter extends RecyclerView.Adapter<ScheduleStaffAdapter.viewHolder>
    {
        private ArrayList<UsersModel> Items;
        private Context context;
        public ScheduleStaffAdapter(ArrayList<UsersModel> items, Context context) {
            Items = items;
            this.context = context;
        }
        @NonNull
        @NotNull
        @Override
        public viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_schedule_staff,parent,false);
            return new viewHolder(view);
        }
        @Override
        public void onBindViewHolder(@NonNull @NotNull viewHolder holder, @SuppressLint("RecyclerView") int position) {
            holder.staff_name.setText(Items.get(position).getName());
            holder.staff_department.setText(Items.get(position).getDepartment());
            holder.btnSchedule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Schedules");

                    HashMap<String,Object> map = new HashMap<>();

                    map.put("day",day);
                    map.put("startTime",startTime);
                    map.put("subjectID",subjectID);
                    map.put("subjectName",subjectName);
                    map.put("endTime",endTime);
                    map.put("staffID",Items.get(position).getUserID());
                    map.put("staffName",Items.get(position).getName());
                    map.put("venue",venue);
                    map.put("department",Items.get(position).getDepartment());

                    reference.child(subjectID).updateChildren(map)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(context, "Staff named "+Items.get(position).getName()+" scheduled for " + subjectName, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            });
        }
        @Override
        public int getItemCount() {
            return Items.size();
        }
        public class viewHolder extends RecyclerView.ViewHolder{
            public TextView staff_name,staff_department,contact;
            public Button btnSchedule;
            public RelativeLayout layout;
            public viewHolder(@NonNull @NotNull View itemView) {
                super(itemView);
                staff_name = itemView.findViewById(R.id.staff_name);
                staff_department = itemView.findViewById(R.id.staff_department);

                btnSchedule = itemView.findViewById(R.id.btnSchedule);
            }
        }
    }
}