package com.example.staffschedulingsystem.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.staffschedulingsystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class EditCourse extends AppCompatActivity {

    private TextInputLayout editcourseName, editcourseCode, editcourseFaculty;
    private String coursename,courseName,courseID,courseCode,coursecode,courseFaculty,coursefaculty;
    private Button btnSubmitChanges;
    private ProgressDialog dialog;
    private ImageView backEditBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);

        Bundle bundle = getIntent().getExtras();
        courseID = bundle.getString("courseID");
        courseCode = bundle.getString("courseCode");
        courseFaculty= bundle.getString("courseFaculty");
        courseName = bundle.getString("courseName");

        btnSubmitChanges = findViewById(R.id.btnSubmitChanges);
        editcourseCode =findViewById(R.id.editcourseCode);
        editcourseName = findViewById(R.id.editSubjectName);
        editcourseFaculty = findViewById(R.id.editcourseFaculty);
        backEditBtn=findViewById(R.id.backEditBtn);
        dialog = new ProgressDialog(this);

        editcourseCode.getEditText().setText(courseCode);
        editcourseName.getEditText().setText(courseName);
        editcourseFaculty.getEditText().setText(courseFaculty);

        btnSubmitChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SubmitCourseChanges();
            }
        });
        backEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    private boolean validateFaculty()
    {
        coursefaculty = editcourseFaculty.getEditText().getText().toString().trim().toUpperCase();
        if(coursefaculty.isEmpty())
        {
            editcourseFaculty.setError("filed must not be empty");
            editcourseFaculty.requestFocus();
            return false;
        }
        else
        {
            editcourseFaculty.setError(null);
            editcourseFaculty.requestFocus();
            return true;
        }
    }
    private boolean validateCode()
    {
        coursename = editcourseName.getEditText().getText().toString().trim().toUpperCase();
        if(coursename.isEmpty())
        {
            editcourseCode.setError("filed must not be empty");
            editcourseCode.requestFocus();
            return false;
        }
        else
        {
            editcourseCode.setError(null);
            editcourseCode.requestFocus();
            return true;
        }
    }
    private boolean validateName()
    {
        coursecode = editcourseCode.getEditText().getText().toString().trim().toUpperCase();
        if(coursecode.isEmpty())
        {
            editcourseName.setError("filed must not be empty");
            editcourseName.requestFocus();
            return false;
        }
        else
        {
            editcourseName.setError(null);
            editcourseName.requestFocus();
            return true;
        }
    }
    private void SubmitCourseChanges()
    {
        if(!validateName() | !validateCode() | !validateFaculty())
        {
            return;
        }
        else
        {
            dialog.setTitle("Update Course");
            dialog.setMessage("Please wait while updating course.......");
            dialog.show();

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Course");
            final HashMap<String,Object> hashMap= new HashMap<>();

            hashMap.put("courseID",courseID);
            hashMap.put("courseName",coursename);
            hashMap.put("courseCode",coursecode);
            hashMap.put("courseFaculty",coursefaculty);

            reference.child(courseID).updateChildren(hashMap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(getApplicationContext(), "Course updated successfully", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(), "Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }
                    });
        }
    }
}