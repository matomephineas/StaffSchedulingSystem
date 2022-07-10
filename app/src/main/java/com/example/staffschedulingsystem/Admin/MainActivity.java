package com.example.staffschedulingsystem.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.staffschedulingsystem.R;
import com.example.staffschedulingsystem.Fragments.CourseMainFragment;
import com.example.staffschedulingsystem.Fragments.StaffMainFragment;
import com.example.staffschedulingsystem.Fragments.StudentMainFragment;
import com.example.staffschedulingsystem.Fragments.TimeTableFragment;
import com.example.staffschedulingsystem.Student.MarkedRegisterFragment;
import com.example.staffschedulingsystem.StudentMarkedAttendanceFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity  {
    BottomNavigationView navigation;
    FrameLayout main_container;
    Fragment active;

    Toolbar toolbar;
    private long pressedTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        //toolbar.setTitle("Wefixit");
        setSupportActionBar(toolbar);

        navigation = findViewById(R.id.navigation1);
        main_container=findViewById(R.id.main_container);

        final Fragment fragment1 = new StaffMainFragment();
        final Fragment fragment2 = new CourseMainFragment();
        final Fragment fragment3 = new StudentMainFragment();
        final Fragment fragment4 = new StudentMarkedAttendanceFragment();

        final FragmentManager fm = getSupportFragmentManager();
        active = fragment1;

        fm.beginTransaction().add(R.id.main_container, fragment4, "4").hide(fragment4).commit();
        fm.beginTransaction().add(R.id.main_container, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.main_container, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.main_container, fragment1, "1").commit();

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @org.jetbrains.annotations.NotNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_view_staff:
                        fm.beginTransaction().hide(active).show(fragment1).commit();
                        active = fragment1;
                        return true;
                    case R.id.nav_view_course:
                        fm.beginTransaction().hide(active).show(fragment2).commit();
                        active = fragment2;
                        return true;
                    case R.id.nav_view_student:
                        fm.beginTransaction().hide(active).show(fragment3).commit();
                        active = fragment3;
                        return true;

                    case R.id.nav_view_marked_register:
                        fm.beginTransaction().hide(active).show(fragment4).commit();
                        active = fragment4;
                        return true;

                }
                return false;
            }
        });

    }

    @Override
    public void onBackPressed() {

        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Click again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }
}