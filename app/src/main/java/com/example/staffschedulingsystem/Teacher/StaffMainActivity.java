package com.example.staffschedulingsystem.Teacher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.Toast;

import com.example.staffschedulingsystem.R;
import com.example.staffschedulingsystem.Student.MarkedRegisterFragment;
import com.example.staffschedulingsystem.Student.StudentMainActivity;
import com.example.staffschedulingsystem.Student.ViewSubjectsFragment;
import com.example.staffschedulingsystem.StudentMarkedAttendanceFragment;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class StaffMainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private TeachingSubjectsFragment viewSubjectsFragment;
    private StudentMarkedAttendanceFragment markedRegisterFragment;
    private long pressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_main);
        viewPager = findViewById(R.id.teacher_viewpager);
        tabLayout = findViewById(R.id.teacher_tabs);

        viewSubjectsFragment = new TeachingSubjectsFragment();
        markedRegisterFragment = new StudentMarkedAttendanceFragment();
        tabLayout.setupWithViewPager(viewPager);
        StaffMainActivity.ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);
        viewPagerAdapter.addFragment(viewSubjectsFragment, "Subjects", R.color.white);
        viewPagerAdapter.addFragment(markedRegisterFragment, "Students Report", R.color.white);

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.getTabAt(0).setIcon(R.drawable.coursevector_24);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_baseline_calendar_month_24);


    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments = new ArrayList<>();
        private List<String> fragmentTitles = new ArrayList<>();
        private List<Integer> fragmentTextColor = new ArrayList<>();

        public ViewPagerAdapter(@NonNull @NotNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void addFragment(Fragment fragment, String title, int black) {
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