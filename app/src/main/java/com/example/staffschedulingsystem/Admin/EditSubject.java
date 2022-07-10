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

public class EditSubject extends AppCompatActivity {

    private TextInputLayout editsubjectName, editsubjectCode, editsubjectFaculty;
    private String subjectname,subjectName,subjectID,subjectCode,subjectcode;
    private Button btnSubmitChanges;
    private ProgressDialog dialog;
    private ImageView backEditBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_subject);

        Bundle bundle = getIntent().getExtras();
        subjectID = bundle.getString("subjectID");
        subjectCode = bundle.getString("subjectCode");
        subjectName = bundle.getString("subjectName");

        editsubjectName = findViewById(R.id.editSubjectName);
        editsubjectCode = findViewById(R.id.editSubjectCode);
        backEditBtn=findViewById(R.id.backEditSubjectBtn);
        btnSubmitChanges = findViewById(R.id.btnSubmitSubjectChanges);

        dialog = new ProgressDialog(this);

        editsubjectCode.getEditText().setText(subjectCode);
        editsubjectName.getEditText().setText(subjectName);


        btnSubmitChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SubmitSubjectChanges();
            }
        });
        backEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private boolean validateCode()
    {
        subjectname = editsubjectName.getEditText().getText().toString().trim().toUpperCase();
        if(subjectname.isEmpty())
        {
            editsubjectCode.setError("filed must not be empty");
            editsubjectCode.requestFocus();
            return false;
        }
        else
        {
            editsubjectCode.setError(null);
            editsubjectCode.requestFocus();
            return true;
        }
    }
    private boolean validateName()
    {
        subjectcode = editsubjectCode.getEditText().getText().toString().trim().toUpperCase();
        if(subjectcode.isEmpty())
        {
            editsubjectName.setError("filed must not be empty");
            editsubjectName.requestFocus();
            return false;
        }
        else
        {
            editsubjectName.setError(null);
            editsubjectName.requestFocus();
            return true;
        }
    }
    private void SubmitSubjectChanges()
    {
        if(!validateName() | !validateCode() )
        {
            return;
        }
        else
        {
            dialog.setTitle("Update Subject");
            dialog.setMessage("Please wait while updating subject.......");
            dialog.show();

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("CourseModules");
            final HashMap<String,Object> hashMap= new HashMap<>();

            hashMap.put("subjectID",subjectID);
            hashMap.put("subjectName",subjectname);
            hashMap.put("subjectCode",subjectcode);

            reference.child(subjectID).updateChildren(hashMap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(getApplicationContext(), "Subject updated successfully", Toast.LENGTH_SHORT).show();
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