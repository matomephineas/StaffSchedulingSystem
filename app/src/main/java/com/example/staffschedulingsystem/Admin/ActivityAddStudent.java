package com.example.staffschedulingsystem.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.staffschedulingsystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ActivityAddStudent extends AppCompatActivity {
    private TextInputLayout reg_student_name,reg_staff_email_address, reg_student_password, reg_student_confirm_password;
    private Button btnCreateAcnt;
    private ProgressDialog progressDialog;
    String name,faculty, courseName, email, password, confirmPassword, courseID;
    private TextView tvCourseName;
    private FirebaseAuth mAuth;
    private ImageView back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        Bundle bundle = getIntent().getExtras();
        courseID = bundle.getString("courseID");
        courseName = bundle.getString("courseName");
        faculty = bundle.getString("faculty");
        mAuth = FirebaseAuth.getInstance();
        tvCourseName = findViewById(R.id.tvCourseName);
        reg_student_name = findViewById(R.id.reg_student_name);
        reg_staff_email_address = findViewById(R.id.reg_staff_email_address);
        reg_student_password = findViewById(R.id.reg_student_password);
        reg_student_confirm_password = findViewById(R.id.reg_student_confirm_password);
        back_btn=findViewById(R.id.back_btn);


        btnCreateAcnt = findViewById(R.id.btn_register_staff);
        progressDialog = new ProgressDialog(this);

        tvCourseName.setText(courseName);
        btnCreateAcnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void createAccount() {
        name = reg_student_name.getEditText().getText().toString().trim();
        email = reg_staff_email_address.getEditText().getText().toString().trim();
        password = reg_student_password.getEditText().getText().toString().trim();
        confirmPassword = reg_student_confirm_password.getEditText().getText().toString().trim();

        if (name.isEmpty()) {
            reg_student_name.setError("filed must not be empty");
            reg_student_name.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            reg_staff_email_address.setError("filed must not be empty");
            reg_staff_email_address.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            reg_staff_email_address.setError("email address is not valid");
            reg_staff_email_address.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            reg_student_password.setError("filed must not be empty");
            reg_student_password.requestFocus();
            return;
        }
        if (password.length() < 5) {
            reg_student_password.setError("password too small");
            reg_student_password.requestFocus();
            return;
        }
        if (confirmPassword.isEmpty()) {
            reg_student_confirm_password.setError("filed must not be empty");
            reg_student_confirm_password.requestFocus();
            return;
        }
        if (!confirmPassword.equals(password)) {
            reg_student_confirm_password.setError("password does not match");
            reg_student_confirm_password.requestFocus();
            return;
        } else {
            progressDialog.setTitle("Create new account");
            progressDialog.setMessage("Please wait while checking your credentials");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                HashMap<String, Object> map = new HashMap<>();
                                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users");

                                map.put("userID", FirebaseAuth.getInstance().getCurrentUser().getUid());
                                map.put("name", name);
                                map.put("email", email);
                                map.put("courseName",courseName);
                                map.put("courseID",courseID);
                                map.put("password", password);
                                map.put("department",faculty);
                                map.put("userType", 2);

                                userRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getApplicationContext(), "Student registered Successful", Toast.LENGTH_LONG).show();
                                            //Intent intent = new Intent(getApplicationContext(), AdminViewCourse.class);
                                            //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            //startActivity(intent);
                                            progressDialog.dismiss();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Error: \n" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                            progressDialog.dismiss();
                                        }
                                    }

                                });
                            } else {
                                String message = task.getException().getMessage();
                                Toast.makeText(getApplicationContext(), "Error: \n" + message, Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }
                    });
        }
    }
}
