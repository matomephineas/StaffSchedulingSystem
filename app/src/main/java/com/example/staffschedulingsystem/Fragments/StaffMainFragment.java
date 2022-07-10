package com.example.staffschedulingsystem.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.staffschedulingsystem.Teacher.AddStaff;
import com.example.staffschedulingsystem.Teacher.ViewStaff;
import com.example.staffschedulingsystem.R;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class StaffMainFragment extends Fragment {


    private ViewPager viewPager;
    private TabLayout tabLayout;

    private AddStaff addStaff;
    private ViewStaff viewStaff;
    private TimeTableFragment timeTableFragment;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_staff_main, container, false);

        viewPager= view.findViewById(R.id.staff_viewpager);
        tabLayout =view.findViewById(R.id.staff_tabs);

        addStaff =new AddStaff();
        viewStaff =new ViewStaff();
        timeTableFragment = new TimeTableFragment();
        tabLayout.setupWithViewPager(viewPager);
        ViewPagerAdapter viewPagerAdapter= new ViewPagerAdapter(getActivity().getSupportFragmentManager(),0);
        viewPagerAdapter.addFragment(addStaff,"Add Staff", R.color.white);
        viewPagerAdapter.addFragment(viewStaff,"View Staff",R.color.white);
        viewPagerAdapter.addFragment(timeTableFragment,"TimeTable",R.color.white);

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_baseline_person_add_24);
        tabLayout.getTabAt(1).setIcon(R.drawable.coursevector_24);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_baseline_calendar_month_24);

        return view;
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
}