package com.example.staffschedulingsystem.Admin;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.staffschedulingsystem.Models.TimeTable;
import com.example.staffschedulingsystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class AddTimeTable extends AppCompatActivity{

    String courseID,courseCode,subjectID,subjectName,subjectCode;
    private TextView textSubjectName,tvSetError,btnAddTimetable,dayoftheweek;
    TextInputLayout textInputVanue;
    int t1Hour,t1Minute,t2Hour,t2Minute;
    Button btnStartTime, btnEndTime,submit;
    ProgressDialog  progressDialog;
    DatabaseReference ref;
    String vanue,day,startTime,endTime;
    String dayType,weekday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_time_table);
        textInputVanue = findViewById(R.id.textInputVanue);
        tvSetError =findViewById(R.id.tvSetError);
        dayoftheweek =findViewById(R.id.dayoftheweek);

        Bundle bundle = getIntent().getExtras();
        //get the data from previous page
        courseID = bundle.getString("courseID");
        courseCode= bundle.getString("courseCode");
        subjectID= bundle.getString("subjectID");
        subjectName = bundle.getString("subjectName");
        subjectCode= bundle.getString("subjectCode");
        weekday=bundle.getString("weekday");
        //assign variables
        btnStartTime = findViewById(R.id.subjectStartTime);
        btnEndTime = findViewById(R.id.subjectEndTime);
        textSubjectName = findViewById(R.id.textSubjectName);
        btnAddTimetable = findViewById(R.id.btnAddTimetable);
        progressDialog = new ProgressDialog(this);
        textSubjectName.setText(subjectName);

        dayoftheweek.setText(weekday);
        btnStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //initialize time picker dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddTimeTable.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                //Initialize hour and minute
                                t1Hour = hourOfDay;
                                t1Minute = minute;
                                //Initialize calender
                                Calendar calendar = Calendar.getInstance();
                                //set hour and minute
                                calendar.set(0,0,0,t1Hour,t1Minute);
                                btnStartTime.setText(DateFormat.format("hh:mm",calendar));
                                startTime= btnStartTime.getText().toString();
                                Toast.makeText(getApplicationContext(), startTime, Toast.LENGTH_SHORT).show();
                            }
                        },12,0,false
                );
                //display previous time selected
                timePickerDialog.updateTime(t1Hour,t1Minute);
                //show diolog
                timePickerDialog.show();

            }
        });
        btnEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //initialize time picker dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        AddTimeTable.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                //Initialize hour and minute
                                t2Hour = hourOfDay;
                                t2Minute = minute;
                                //Initialize calender
                                Calendar calendar = Calendar.getInstance();
                                //set hour and minute
                                calendar.set(0,0,0,t2Hour,t2Minute);
                                //set selected time on textview
                                btnEndTime.setText(DateFormat.format("hh:mm",calendar));
                                endTime = btnEndTime.getText().toString();
                            }
                        },12,0,false
                );
                //display previous time selected
                timePickerDialog.updateTime(t2Hour,t2Minute);
                //show diolog
                timePickerDialog.show();

            }
        });
        btnAddTimetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTimeTable();
            }
        });
    }

    private boolean validateVenue()
    {
        vanue =textInputVanue.getEditText().getText().toString().trim();

        if(vanue.isEmpty())
        {
            textInputVanue.setError("filed must not be empty");
            textInputVanue.requestFocus();
            return false;
        }
        else if(vanue.matches("[0-9]+"))
        {
            textInputVanue.setError(null);
            textInputVanue.requestFocus();
            return true;

        }
        else
        {
            tvSetError.setVisibility(View.INVISIBLE);
            textInputVanue.setError(null);
            textInputVanue.requestFocus();
            return true;
        }
    }
    private boolean validateTime()
    {
        if(btnStartTime.getText().equals("Select Start time") && btnEndTime.getText().equals("Select End time"))
        {
            tvSetError.setText("Please select time. Either start or end time");
            tvSetError.setVisibility(View.VISIBLE);
            return false;
        }
        if(startTime.equals(endTime))
        {
            tvSetError.setText("Start time and End time connot be equal,Please select other times");
            tvSetError.setVisibility(View.VISIBLE);
            return false;
        }

        else
        {
            tvSetError.setVisibility(View.INVISIBLE);
            return true;
        }
    }

    private void addTimeTable()
    {

      if(!validateVenue() | !validateTime()){
          return;
      }
      else
      {
          String day;
          Date date = new Date();
          day = new SimpleDateFormat("EEEE").format(date);

          DatabaseReference reference = FirebaseDatabase.getInstance().getReference("TimeTable").push();
          String key = reference.getKey();

          HashMap<String,Object> map = new HashMap<>();

          map.put("timeTableId",key);
          map.put("courseCode",courseCode);
          map.put("courseID",courseID);
          map.put("endTime",endTime);
          map.put("subjCode",subjectCode);
          map.put("subjDay",weekday);
          map.put("subjName",subjectName);
          map.put("startTime",startTime);
          map.put("subjID",subjectID);
          map.put("subjVanue",vanue);

          reference.updateChildren(map)
                  .addOnCompleteListener(new OnCompleteListener<Void>() {
                      @Override
                      public void onComplete(@NonNull Task<Void> task)
                      {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(), "TimeTable added successfully", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }else
                        {
                            Toast.makeText(getApplicationContext(), "Error :"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                      }
                  });


      }

    }


}