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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class donationDetails extends AppCompatActivity {
    ImageButton add, backButton;
    FirebaseAuth fAuth;
    String userID;
    FirebaseFirestore fStore;
    //for map
    TextView showOnMap,addContactPageLocation,afterAdd;
    FusedLocationProviderClient fusedLocationProviderClient;
    public String etSource,etDestination;
    public Double lat,lon;
    public Double lat1,lon1;
    public String tempSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_details);
        //for map
        showOnMap = (TextView) findViewById(R.id.showOnMap);
        addContactPageLocation = (TextView)findViewById(R.id.addContactPageLocation);
        afterAdd = (TextView)findViewById(R.id.afterAdded);
        backButton = findViewById(R.id.backButton);

        //for map

        add=findViewById(R.id.addToWishList);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(donationDetails.this,DonationCategory.class );
                startActivity(intent);
                finish();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ValidatePostInfo();
            }
        });
        getIncomingIntent();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(
                donationDetails.this
        );
        initiateLocationRetreiving();
        showOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisplayTrack(etSource,etDestination);
            }
        });
    }

    private void initiateLocationRetreiving() {
        //chcek condition
        if (ActivityCompat.checkSelfPermission(donationDetails.this
                , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(donationDetails.this
                , Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
            //System.out.println("On click");
           // lat1 = lat;
           // lon1 = lon;
            //System.out.println(lat1);
            //System.out.println(lon1);
            //etDestination = addContactPageLocation.getText().toString();
            //System.out.println(tempSource);
            //etSource = tempSource;
            //etSource = getCityName(lat1,lon1);

        }
        else
        {
            //when permission not granted
            //request Permission

            ActivityCompat.requestPermissions(donationDetails.this
                    ,new String[]{Manifest.permission.ACCESS_FINE_LOCATION
                            ,Manifest.permission.ACCESS_COARSE_LOCATION}
                    ,100);

        }
    }

    private String getCityName(double latitude,double longitude) {
        String myCity = "";
        Geocoder geocoder = new Geocoder(donationDetails.this, Locale.getDefault());
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
                        etDestination = addContactPageLocation.getText().toString();


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
                                etDestination = addContactPageLocation.getText().toString();
                            }
                        };
                        //Request location updates
                        if (ActivityCompat.checkSelfPermission(donationDetails.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(donationDetails.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
        if(getIntent().hasExtra("location1") && getIntent().hasExtra("cat1")&& getIntent().hasExtra("des1")&& getIntent().hasExtra("lastDate1")&& getIntent().hasExtra("name1")&& getIntent().hasExtra("title1"))
        {
            String location=getIntent().getStringExtra("location1");
            String category=getIntent().getStringExtra("cat1");
            String des=getIntent().getStringExtra("des1");
            String lastDate=getIntent().getStringExtra("lastDate1");
            String name=getIntent().getStringExtra("name1");
            String title=getIntent().getStringExtra("title1");
            setValue(location,category,des,lastDate,name,title);
        }
    }

    private void setValue(String location,String category,String des,String lastDate,String name,String title){
        TextView addDonationTo=findViewById(R.id.addDonationTo);
        addDonationTo.setText(name);
        TextView addDonationTitle =findViewById(R.id.addDonationTitle);
        addDonationTitle.setText(title);
        TextView addDonationDescription=findViewById(R.id.addDonationDescription);
        addDonationDescription.setText(des);
        TextView addLastDateDetails=findViewById(R.id.addLastDateDetails);
        addLastDateDetails.setText(lastDate);
        TextView addContactPageLocation=findViewById(R.id.addContactPageLocation);
        addContactPageLocation.setText(location);
        TextView addCategory=findViewById(R.id.addCategory);
       addCategory.setText(category);

    }
    private void ValidatePostInfo() {
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        TextView addDonationTo=findViewById(R.id.addDonationTo);

        TextView addDonationTitle =findViewById(R.id.addDonationTitle);

        TextView addDonationDescription=findViewById(R.id.addDonationDescription);

        TextView addLastDateDetails=findViewById(R.id.addLastDateDetails);

        TextView addContactPageLocation=findViewById(R.id.addContactPageLocation);
        TextView category=findViewById(R.id.addCategory);


        //String userCategory = category.getSelectedItem().toString();
        String orgName1 = addDonationTo.getText().toString();
        String description = addDonationDescription.getText().toString();
        String userLocation = addContactPageLocation.getText().toString();
        String postTitle = addDonationTitle.getText().toString();
        String donationDate = addLastDateDetails.getText().toString();
        String cat = category.getText().toString();

        //StoringImageToFirebaseStorage();
        userID = fAuth.getCurrentUser().getUid();
        final int min = 1;
        final int max = 1000;
        final int random = new Random().nextInt((max - min) + 1) + min;
        String postId = userID+random;

        DocumentReference documentReference = fStore.collection("Wishlist").document(postId);
        Map<String, Object> postMap = new HashMap<>();
        postMap.put("userID", postId);
        postMap.put("organizationName", orgName1);
        postMap.put("description", description);
        postMap.put("Location", userLocation);
        postMap.put("donationDate", donationDate);
        postMap.put("title", postTitle);
        postMap.put("category", cat);


        documentReference.set(postMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                add.setImageResource(R.drawable.added);
                afterAdd.setText("Added to wishlist");
                //Toast.makeText(donationDetails.this, "the WishList is created successfully", Toast.LENGTH_LONG).show();
            }
        });
        //Intent intent = new Intent(donationDetails.this, donationDetails.class );
        //startActivity(intent);
        //finish();

    }
}