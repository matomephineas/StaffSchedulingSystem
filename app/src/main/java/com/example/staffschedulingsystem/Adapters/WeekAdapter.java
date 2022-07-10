package com.example.staffschedulingsystem.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.staffschedulingsystem.Admin.AddTimeTable;
import com.example.staffschedulingsystem.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class WeekAdapter extends RecyclerView.Adapter<WeekAdapter.ViewHolder> {
     Context context;
    ArrayList weeksList, images;
    String courseID,courseCode,subjectID,subjectName,subjectCode;

    public WeekAdapter(Context context, ArrayList weeksList, ArrayList images, String courseID, String courseCode, String subjectID, String subjectName, String subjectCode) {
        this.context = context;
        this.weeksList = weeksList;
        this.images = images;
        this.courseID = courseID;
        this.courseCode = courseCode;
        this.subjectID = subjectID;
        this.subjectName = subjectName;
        this.subjectCode = subjectCode;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_day_activity,parent,false);
        //sharedPreferences= context.getSharedPreferences("MyDay",Context.MODE_PRIVATE);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, @SuppressLint("RecyclerView") int position)
    {
        int res = (int) images.get(position);
        holder.imageView.setImageResource(res);
        holder.weekday.setText((String) weeksList.get(position));
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddTimeTable.class);
                intent.putExtra("weekday",(String) weeksList.get(position));
                intent.putExtra("courseID",courseID);
                intent.putExtra("courseCode",courseCode);
                intent.putExtra("subjectID",subjectID);
                intent.putExtra("subjectName",subjectName);
                intent.putExtra("subjectCode",subjectCode);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return weeksList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView weekday;
        public ImageView imageView;
        public RelativeLayout layout;
        public ViewHolder(@NonNull @NotNull View itemView)
        {
            super(itemView);
            weekday = itemView.findViewById(R.id.tvDay);
            imageView = itemView.findViewById(R.id.dayImg);
            layout = itemView.findViewById(R.id.layout);
        }
    }
}
