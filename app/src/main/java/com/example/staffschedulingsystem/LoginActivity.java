package com.example.staffschedulingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.staffschedulingsystem.Admin.MainActivity;
import com.example.staffschedulingsystem.Models.UsersModel;
import com.example.staffschedulingsystem.Student.StudentMainActivity;
import com.example.staffschedulingsystem.Teacher.StaffMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class LoginActivity extends AppCompatActivity {

    TextView btnCreateAcount;
    Button btn_login;
    EditText loginUsername,regEditPassword;
    private String username,password,usertype;
    private ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginUsername = findViewById(R.id.loginEmail);
        regEditPassword =findViewById(R.id.loginPassword);
        btn_login =findViewById(R.id.btn_school_login);
        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                signInUser();
            }
        });

    }
    private boolean validateUsername()
    {
        username =loginUsername.getText().toString().trim();
        if(username.isEmpty())
        {
            loginUsername.setError("filed must not be empty");
            loginUsername.requestFocus();
            return false;
        }
        else
        {
            loginUsername.setError(null);
            loginUsername.requestFocus();
            return true;
        }
    }
    private boolean validatePassword()
    {
        password =regEditPassword.getText().toString().trim();
        if(password.isEmpty())
        {
            regEditPassword.setError("filed must not be empty");
            regEditPassword.requestFocus();
            return false;
        }
        if(password.length() < 5)
        {
            regEditPassword.setError("password too small");
            regEditPassword.requestFocus();
            return false;
        }
        else
        {
            regEditPassword.setError(null);
            regEditPassword.requestFocus();
            return true;
        }
    }

    private void signInUser()
    {
        if(!validateUsername() | !validatePassword())
        {
            return;
        }
        else
        {

            progressDialog.setTitle("Login Page");
            progressDialog.setMessage("Please wait while we log you in");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            progressDialog.show();
            mAuth.signInWithEmailAndPassword(username,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<AuthResult> task)
                        {
                            if(task.isSuccessful())
                            {
                                mUser =mAuth.getCurrentUser();
                                usertype = mUser.getUid();
                                checkUserType(usertype);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }
                    });

        }
    }

    private void checkUserType(String mUser)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(mUser)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists())
                        {
                            if(snapshot.exists())
                            {

                                UsersModel userProfile = snapshot.getValue(UsersModel.class);
                                //assert userProfile != null;
                                int userType = (userProfile.getUserType());
                                switch (userType) {
                                    case 3:
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        intent.putExtra("userId",userProfile.getUserID());
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        progressDialog.dismiss();
                                        break;
                                    case 2:
                                        Intent intent0 = new Intent(getApplicationContext(), StudentMainActivity.class);
                                        intent0.putExtra("userId",userProfile.getUserID());
                                        intent0.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent0);
                                        progressDialog.dismiss();
                                        break;
                                    case 1:
                                        Intent intent1 = new Intent(getApplicationContext(), StaffMainActivity.class);
                                        intent1.putExtra("userId",userProfile.getUserID());
                                        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent1);
                                        progressDialog.dismiss();
                                        break;
                                }
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "User: "+ username +" does not exits", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

}