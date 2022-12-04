package com.example.charity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class eventDetailsOrg extends AppCompatActivity {
    ImageView backButton;
    TextView showOnMap,showLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    public String etSource,etDestination;
    public Double lat,lon;
    public Double lat1,lon1;
    public String tempSource;
    //for notification
    FirebaseAuth fAuth;
    String userID;
    FirebaseFirestore fStore;
    Button goingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details_org);
        goingButton=findViewById(R.id.goingButton);
        goingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidatePostInfo();
            }
        });

        //for map
        showOnMap = (TextView) findViewById(R.id.showOnMap);
        showLocation = (TextView)findViewById(R.id.showLocation);
        //for map
        getIncomingIntent();
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(eventDetailsOrg.this,org_event.class );
                startActivity(intent);
                finish();
            }
        });
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(
                eventDetailsOrg.this
        );
        initiateLocationRetreiving();
        showOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisplayTrack(etSource,etDestination);
            }
        });
    }

    //notification .....
    private void ValidatePostInfo() {
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        TextView showTitle=findViewById(R.id.showEventTitle);
        TextView showStartDate =findViewById(R.id.showStartDate);
        TextView showLocation=findViewById(R.id.showLocation);

        String title = showTitle.getText().toString();
        String startDate = showStartDate.getText().toString();
        String location = showLocation.getText().toString();

        userID = fAuth.getCurrentUser().getUid();
        final int min = 1;
        final int max = 1000;
        final int random = new Random().nextInt((max - min) + 1) + min;
        String postId = userID+random;

        DocumentReference documentReference = fStore.collection("Notification").document(postId);
        Map<String, Object> postMap = new HashMap<>();
        postMap.put("userID", userID);
        postMap.put("title", title);
        postMap.put("startDate", startDate);
        postMap.put("Location", location);


        documentReference.set(postMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                Toast.makeText(eventDetailsOrg.this, "You are going", Toast.LENGTH_LONG).show();
            }
        });
        Intent intent = new Intent(eventDetailsOrg.this, eventDetailsOrg.class );
        startActivity(intent);
        finish();

    }
    private void initiateLocationRetreiving() {
        //chcek condition
        if (ActivityCompat.checkSelfPermission(eventDetailsOrg.this
                , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(eventDetailsOrg.this
                , Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        }
        else
        {
            //when permission not granted
            //request Permission

            ActivityCompat.requestPermissions(eventDetailsOrg.this
                    ,new String[]{Manifest.permission.ACCESS_FINE_LOCATION
                            ,Manifest.permission.ACCESS_COARSE_LOCATION}
                    ,100);

        }
    }

    private String getCityName(double latitude,double longitude) {
        String myCity = "";
        Geocoder geocoder = new Geocoder(eventDetailsOrg.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude,longitude,1);
            String address = addresses.get(0).getAddressLine(0);
            //String city = addresses.get(0).getLocality();
            //String state = addresses.get(0).getAdminArea();
            //String zip = addresses.get(0).getPostalCode();
            //String country = addresses.get(0).getCountryName();

            //myCity = address+city+state+country;
            myCity = address;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myCity;
    }

    private void DisplayTrack(String etSource, String etDestination) {
        //if device does not have map then redirect to play store
        try{
            //when map installed
            //initialize uri
            Uri uri = Uri.parse("https://www.google.co.in/maps/dir/"+etSource+"/"+etDestination);

            //Initiialize intent with action view
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            //set package
            intent.setPackage("com.google.android.apps.maps");
            //set flag
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //start activity
            startActivity(intent);
        }
        catch (ActivityNotFoundException e)
        {
            //when map not installed
            //initialize uri
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
            //Initiialize intent with action view
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            //set flag
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //start activity
            startActivity(intent);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //check permission
        if(requestCode == 100 && grantResults.length>0 && (grantResults[0] + grantResults[1]
                == PackageManager.PERMISSION_GRANTED)){
            //when permisssion granted
            getCurrentLocation();
        }
        else {
            //if not granted
            Toast.makeText(getApplicationContext(),"Permission for location access denied"
                    ,Toast.LENGTH_SHORT).show();
        }
    }

    private void getCurrentLocation() {
        //Initialize location manager
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //chcek condition
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    //Initialize location
                    Location location = task.getResult();

                    //check condition
                    if (location != null) {
                        lat = location.getLatitude();
                        lon = location.getLongitude();
                        lat1 = lat;
                        lon1 = lon;
                        System.out.println("On complete");
                        System.out.println(lat1);
                        System.out.println(lon1);
                        etSource = getCityName(lat1,lon1);
                        etDestination = showLocation.getText().toString();


                    } else {
                        LocationRequest locationRequest = new LocationRequest()
                                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                .setInterval(10000)
                                .setFastestInterval(10000)
                                .setNumUpdates(1);

                        //Initialize location call back
                        LocationCallback locationCallback = new LocationCallback() {
                            public void onLocationResult(LocationResult locationResult) {
                                Location location1 = locationResult.getLastLocation();
                                lat = location.getLatitude();
                                lon = location.getLongitude();
                                lat1 = lat;
                                lon1 = lon;
                                System.out.println("On callback");
                                System.out.println(lat1);
                                System.out.println(lon1);
                                etSource = getCityName(lat1,lon1);
                                etDestination = showLocation.getText().toString();
                            }
                        };
                        //Request location updates
                        if (ActivityCompat.checkSelfPermission(eventDetailsOrg.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(eventDetailsOrg.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                    }
                }
            });
        }
        else {
            //When location service is not enabled
            //open location setting
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

    private void getIncomingIntent(){

        if(getIntent().hasExtra("eventLocation1") && getIntent().hasExtra("eventCategory1")&& getIntent().hasExtra("eventDescription1")&& getIntent().hasExtra("endDate1")&& getIntent().hasExtra("endTime1")&& getIntent().hasExtra("startDate1")&& getIntent().hasExtra("startTime1")&& getIntent().hasExtra("eventTitle1"))
        {
            String loc=getIntent().getStringExtra("eventLocation1");
            String cat=getIntent().getStringExtra("eventCategory1");
            String des=getIntent().getStringExtra("eventDescription1");
            String eD=getIntent().getStringExtra("endDate1");
            String eT=getIntent().getStringExtra("endTime1");
            String sD=getIntent().getStringExtra("startDate1");
            String sT=getIntent().getStringExtra("startTime1");
            String title=getIntent().getStringExtra("eventTitle1");
            setValue(loc,cat,des,eD,eT,sD,sT,title);
        }
    }

    private void setValue(String loc,String cat,String des,String eD,String eT,String sD,String sT,String title){
        TextView showLocation=findViewById(R.id.showLocation);
        showLocation.setText(loc);
        TextView showEventCategory =findViewById(R.id.showEventCategory);
        showEventCategory.setText(cat);
        TextView showEventDescription=findViewById(R.id.showEventDescription);
        showEventDescription.setText(des);
        TextView showEndDate=findViewById(R.id.showEndDate);
        showEndDate.setText(eD);
        TextView showEndTime=findViewById(R.id.showEndTime);
        showEndTime.setText(eT);

        TextView showStartDate=findViewById(R.id.showStartDate);
        showStartDate.setText(sD);
        TextView showStartTime=findViewById(R.id.showStartTime);
        showStartTime.setText(sT);
        TextView showEventTitle=findViewById(R.id.showEventTitle);
        showEventTitle.setText(title);


    }
}