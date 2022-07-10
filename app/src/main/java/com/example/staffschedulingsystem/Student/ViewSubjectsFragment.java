package com.example.staffschedulingsystem.Student;

import static android.content.Context.LOCATION_SERVICE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.PermissionRequest;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.staffschedulingsystem.Models.TimeTable;
import com.example.staffschedulingsystem.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.single.PermissionListener;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class ViewSubjectsFragment extends Fragment {


    private RecyclerView recyclerView;
    private DatabaseReference retrieve;
    private StudentTimeTableAdapter adapter;
    private ArrayList<TimeTable> mList;
    ImageView report,generateReport;
    TextView studentfaculty,studentcourse,studentname;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    String day,userId,name,lan, lon,address1;
    int hours;
    private boolean gps_enabled = false;
    private boolean network_enabled = false;
    public LocationManager locationManager;
    boolean isPermissionGranted;
    private FusedLocationProviderClient mLoactionClient;
    private int GPS_REQUEST_CODE =9001;
    public LocationListener locationListener = new MyLocationListener();
    Geocoder geocoder;
    List<Address> myAddress;
    private GoogleMap mGoogleMap;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_view_subjects, container, false);
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        hours = c.get(Calendar.HOUR_OF_DAY);
        day = new SimpleDateFormat("EEEE").format(date);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        recyclerView =view.findViewById(R.id.studenttimetable);
        studentname =view.findViewById(R.id.studentname);
        studentcourse =view.findViewById(R.id.studentcourse);
        studentfaculty =view.findViewById(R.id.studentfaculty);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        Bundle bundle = getActivity().getIntent().getExtras();
        userId = bundle.getString("userId");
        mList = new ArrayList<>();
        adapter = new StudentTimeTableAdapter(mList,getContext(),userId,name);
        recyclerView.setAdapter(adapter);

        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Users");
        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    name = snapshot.child("name").getValue().toString();
                    String course = snapshot.child("courseName").getValue().toString();
                    String department = snapshot.child("department").getValue().toString();

                    studentcourse.setText(course);
                    studentfaculty.setText(department);
                    studentname.setText(name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        retrieve = FirebaseDatabase.getInstance().getReference("TimeTable");
        retrieve.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @com.google.firebase.database.annotations.NotNull DataSnapshot snapshot) {
                mList.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren())
                {
                    //it gets the data from the Time table class and puts them in an adapter
                    TimeTable timeTable = dataSnapshot.getValue(TimeTable.class);
                    mList.add(timeTable);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull @com.google.firebase.database.annotations.NotNull DatabaseError error) {
            }
        });


        return view;
    }


    public class StudentTimeTableAdapter extends RecyclerView.Adapter<StudentTimeTableAdapter.ViewHolder> {
        private ArrayList<TimeTable> Items;
        private Context context;
        private String userId, name;



        public StudentTimeTableAdapter(ArrayList<TimeTable> items, Context context, String userId, String name) {
            Items = items;
            this.context = context;
            this.userId = userId;
            this.name = name;
        }

        @NonNull
        @NotNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_markregistersubject, parent, false);
            checkMyLocation();
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            getMyLocation();
            //getCurrentLocation();
            isGPSenable();
            checkPermissionLocation();

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            holder.SubjectName.setText(Items.get(position).getSubjName());
            holder.SubjectVanue.setText(Items.get(position).getSubjVanue());
            holder.SubjectDay.setText(Items.get(position).getSubjDay());
            holder.SubjectTime.setText(Items.get(position).getStartTime() + " - " + Items.get(position).getEndTime());
            holder.btnMarkRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Date date = new Date();
                    day = new SimpleDateFormat("EEEE").format(date);

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat CurrentDate = new SimpleDateFormat("MMM dd, yyyy");
                    String saveCurrentDate = CurrentDate.format(calendar.getTime());

                    SimpleDateFormat CurrentTime = new SimpleDateFormat("HH:MM a");
                    String saveCurrentTime = CurrentTime.format(calendar.getTime());

                     DatabaseReference refUser = FirebaseDatabase.getInstance().getReference("Users");
                     refUser.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                         @Override
                         public void onDataChange(@NonNull DataSnapshot snapshot)
                         {
                           String name = snapshot.child("name").getValue().toString();

                             DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Register Marked").push();
                             HashMap<String, Object> map = new HashMap<>();

                             String key = ref.getKey();
                             map.put("subjectName", Items.get(position).getSubjName());
                             map.put("subjectID", Items.get(position).getSubjID());
                             map.put("dateMarked", saveCurrentDate);
                             map.put("timeMarked", saveCurrentTime);
                             map.put("status", "Attended");
                             map.put("studentName", name);
                             map.put("userId", userId);
                             map.put("address",address1);
                             map.put("key",key);

                             ref.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                 @Override
                                 public void onComplete(@NonNull Task<Void> task) {
                                     if (task.isSuccessful()) {
                                         Toast.makeText(context, "Subject marked successfully", Toast.LENGTH_SHORT).show();
                                     } else {
                                         Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                     }
                                 }

                             });

                         }

                         @Override
                         public void onCancelled(@NonNull DatabaseError error) {

                         }
                     });

                }
            });

        }

        @Override
        public int getItemCount() {
            return Items.size();
        }

        public  class ViewHolder extends RecyclerView.ViewHolder {
            public TextView SubjectName, SubjectVanue, SubjectTime, SubjectDay;
            public ImageView userimage;
            public Button btnMarkRegister;
            public RelativeLayout layout;

            public ViewHolder(@NonNull @NotNull View itemView) {
                super(itemView);
                SubjectName = itemView.findViewById(R.id.tvSubjectName);
                SubjectVanue = itemView.findViewById(R.id.tvSubjectVanue);
                SubjectTime = itemView.findViewById(R.id.tvSubjectTime);
                //userimage = itemView.findViewById(R.id.accnt_image);
                layout = itemView.findViewById(R.id.ttlayout);
                SubjectDay = itemView.findViewById(R.id.SubjectDay);
                btnMarkRegister = itemView.findViewById(R.id.btnMarkRegister);
            }
        }

//        public void showSettingsAlert() {
//            AlertDialog.Builder alertDialog = new AlertDialog.Builder(
//                    context);
//            alertDialog.setTitle("SETTINGS");
//            alertDialog.setMessage("Enable Location Provider! Go to settings menu?");
//            alertDialog.setPositiveButton("Settings",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                            context.startActivity(intent);
//                        }
//                    });
//            alertDialog.setNegativeButton("Cancel",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.cancel();
//                        }
//                    });
//            alertDialog.show();
//        }
//
//        private class GeocoderHandler extends Handler {
//            @Override
//            public void handleMessage(Message message) {
//
//                switch (message.what) {
//                    case 1:
//                        Bundle bundle = message.getData();
//                        locationAddress = bundle.getString("address");
//                        break;
//                    default:
//                        locationAddress = null;
//                }
//
//            }
//        }

    }
    private boolean isGPSenable()
    {
        LocationManager locationManager =(LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        boolean providerEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(providerEnabled)
        {
            return true;
        }
        else
        {
            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());
            builder.setTitle("GPS Permission");
            builder.setMessage("GPS is reuired for this app to work. Please enable GPS");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    Intent intent =new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);

                    startActivityForResult(intent,GPS_REQUEST_CODE);
                }
            }).setCancelable(false)
                    .show();

        }
        return false;
    }
    private void checkMyLocation()
    {
        Dexter.withContext(getActivity()).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse)
            {
                Toast.makeText(getActivity(), "Permission granted", Toast.LENGTH_SHORT).show();
                isPermissionGranted = true;

            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri =Uri.fromParts("package",getActivity().getPackageName(),"fragment");
                intent.setData(uri);
                startActivity(intent);
            }

            @Override
            public void onPermissionRationaleShouldBeShown(com.karumi.dexter.listener.PermissionRequest permissionRequest, PermissionToken permissionToken) {

            }
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }
    @SuppressLint("MissingPermission")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==GPS_REQUEST_CODE)
        {
            LocationManager locationManager = (LocationManager)getActivity().getSystemService(LOCATION_SERVICE);
            boolean providerEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if(providerEnable)
            {
                Toast.makeText(getActivity(), "GPS is enable", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getActivity(), "GPS is not enabled", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public class MyLocationListener implements LocationListener
    {
        @Override
        public void onLocationChanged(@NonNull Location location)
        {
            if(location !=null)
            {
                locationManager.removeUpdates(locationListener);
                lan =""+ location.getLatitude();
                lon =""+location.getLongitude();

                geocoder =new Geocoder(getActivity(), Locale.getDefault());
                try {
                    myAddress=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                address1=myAddress.get(0).getAddressLine(0);
                //address.setText(address1);
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
        @Override
        public void onProviderEnabled(@NonNull String provider) {

        }
        @Override
        public void onProviderDisabled(@NonNull String provider) {

        }
    }
    private void getMyLocation() {
        try {
            gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        } catch (Exception e) {
        }
        try {
            network_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        } catch (Exception e) {
        }

        if (!gps_enabled && !network_enabled) {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
            builder.setTitle("Attention");
            builder.setMessage("Sorry,Location services is not enabled ");
            builder.create().show();
        }
        if (gps_enabled) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        }
        if(network_enabled)
        {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        }
    }
    private boolean checkPermissionLocation()
    {
        int location = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);
        int location2 = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION);

        List<String> listPermission= new ArrayList<>();
        if(location != PackageManager.PERMISSION_GRANTED){
            listPermission.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(location2 != PackageManager.PERMISSION_GRANTED){
            listPermission.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if(!listPermission.isEmpty())
        {
            ActivityCompat.requestPermissions(getActivity(),listPermission.toArray(new String[listPermission.size()]),1);
        }
        return true;
    }
}
