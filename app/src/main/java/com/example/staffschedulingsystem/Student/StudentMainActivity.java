package com.example.staffschedulingsystem.Student;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import com.example.staffschedulingsystem.R;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class StudentMainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private long pressedTime;

    private ViewSubjectsFragment viewSubjectsFragment;
    private MarkedRegisterFragment markedRegisterFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);

        viewPager= findViewById(R.id.student_viewpager);
        tabLayout =findViewById(R.id.student_tabs);

        viewSubjectsFragment =new ViewSubjectsFragment();
        markedRegisterFragment =new MarkedRegisterFragment();
        tabLayout.setupWithViewPager(viewPager);
        ViewPagerAdapter viewPagerAdapter= new ViewPagerAdapter(getSupportFragmentManager(),0);
        viewPagerAdapter.addFragment(viewSubjectsFragment,"TimeTable", R.color.white);
        viewPagerAdapter.addFragment(markedRegisterFragment,"Attendance",R.color.white);

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.getTabAt(0).setIcon(R.drawable.coursevector_24);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_baseline_calendar_month_24);


    }

    private class ViewPagerAdapter extends FragmentPagerAdapter
    {
        private List<Fragment> fragments=new ArrayList<>();
        private List<String> fragmentTitles=new ArrayList<>();
        private List<Integer> fragmentTextColor=new ArrayList<>();

        public ViewPagerAdapter(@NonNull @NotNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }
        public void addFragment(Fragment fragment, String title, int black)
        {
            fragments.add(fragment);
            fragmentTitles.add(title);
            fragmentTextColor.add(R.color.black);
        }
        @NonNull
        @NotNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @org.jetbrains.annotations.Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitles.get(position);
        }
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