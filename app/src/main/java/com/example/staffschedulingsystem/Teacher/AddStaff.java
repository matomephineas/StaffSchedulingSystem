package com.example.staffschedulingsystem.Teacher;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.staffschedulingsystem.Admin.MainActivity;
import com.example.staffschedulingsystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class AddStaff extends Fragment {

    private TextInputLayout regEditName,regEditEmail, reg_staff_department,regEditContact,regEditPassword,regEditConfirmPassword;
    private TextView alrdyRegistered,userView;
    private Button btn_register_staff;
    private ProgressDialog progressDialog;

    boolean isValid = true;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DatabaseReference userRef;
    String name, department,phone,email,password,confirmpassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_add_staff, container, false);
        mAuth = FirebaseAuth.getInstance();

        regEditName =view.findViewById(R.id.edit_reg_staff_name);
        regEditEmail =view.findViewById(R.id.reg_staff_email_address);
        regEditContact =view.findViewById(R.id.reg_staff_phone);
        regEditPassword =view.findViewById(R.id.reg_staff_password);
        reg_staff_department =view.findViewById(R.id.reg_staff_department);
        regEditConfirmPassword =view.findViewById(R.id.reg_staff_confirm_password);

        btn_register_staff = view.findViewById(R.id.btn_register_staff);
        progressDialog = new ProgressDialog(getContext());

        btn_register_staff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });
        return view;
    }
    private void createAccount()
    {
        name =regEditName.getEditText().getText().toString().trim();
        phone =regEditContact.getEditText().getText().toString().trim();
        email =regEditEmail.getEditText().getText().toString().trim();
        password =regEditPassword.getEditText().getText().toString().trim();
        confirmpassword =regEditConfirmPassword.getEditText().getText().toString().trim();
        department = reg_staff_department.getEditText().getText().toString().trim();

        if(name.isEmpty())
        {
            regEditName.setError("filed must not be empty");
            regEditName.requestFocus();
            return;
        }
        if(department.isEmpty())
        {
            reg_staff_department.setError("filed must not be empty");
            reg_staff_department.requestFocus();
            return;
        }
        if(email.isEmpty())
        {
            regEditEmail.setError("filed must not be empty");
            regEditEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            regEditEmail.setError("email address is not valid");
            regEditEmail.requestFocus();
            return;
        }

        if(phone.isEmpty())
        {
            regEditContact.setError("filed must not be empty");
            regEditContact.requestFocus();
            return;
        }
        if(!Patterns.PHONE.matcher(phone).matches())
        {
            regEditContact.setError("Phone number is invalid");
            regEditContact.requestFocus();
            return;
        }
        if(password.isEmpty())
        {
            regEditPassword.setError("filed must not be empty");
            regEditPassword.requestFocus();
            return;
        }
        if(password.length() < 5)
        {
            regEditPassword.setError("password too small");
            regEditPassword.requestFocus();
            return;
        }
        if(confirmpassword.isEmpty())
        {
            regEditConfirmPassword.setError("filed must not be empty");
            regEditConfirmPassword.requestFocus();
            return;
        }
        if(!confirmpassword.equals(password))
        {
            regEditConfirmPassword.setError("password does not match");
            regEditConfirmPassword.requestFocus();
            return;
        }

        else
        {
            progressDialog.setTitle("Create new account");
            progressDialog.setMessage("Please wait while checking your credentials");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                HashMap<String,Object> map = new HashMap<>();

                                DatabaseReference userRef =  FirebaseDatabase.getInstance().getReference("Users");

                                map.put("userID",FirebaseAuth.getInstance().getCurrentUser().getUid());
                                map.put("name",name);
                                map.put("phone",phone);
                                map.put("email",email);
                                map.put("deparment",department);
                                map.put("password",password);
                                map.put("userType",1);

                                userRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task)
                                    {
                                        if(task.isSuccessful())
                                        {
                                            Toast.makeText(getContext(), "Staff Registered Successfull", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(getContext(), MainActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                            progressDialog.dismiss();
                                        }
                                        else
                                        {
                                            Toast.makeText(getContext(), "Error: \n" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                            progressDialog.dismiss();
                                        }
                                    }

                                });
                            }
                            else
                            {
                                String message = task.getException().getMessage();
                                Toast.makeText(getContext(), "Error: \n" + message, Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }
                    });
        }
    }

}