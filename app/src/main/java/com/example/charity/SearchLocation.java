package com.example.charity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.SearchView;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SearchLocation extends FragmentActivity implements OnMapReadyCallback {
    GoogleMap map;
    SupportMapFragment mapFragment;
    SearchView searchView;
    String value;
    int resCode=102;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_location);

        searchView = findViewById(R.id.sv_location);
        mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.google_map);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = searchView.getQuery().toString();
                List<Address> addressList = null;
                if(location != null || !location.equals(""))
                {
                    Geocoder geocoder = new Geocoder(SearchLocation.this);

                    try {
                        addressList = geocoder.getFromLocationName(location,1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(),address.getLongitude());
                    //for address
                    String cityName = getCityName(latLng);
                    //
                    map.addMarker(new MarkerOptions().position(latLng).title(location));
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));

                    map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {

                            String i = String.valueOf(latLng);
                            setLocation(i,cityName);
                            return false;
                        }
                    });
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        mapFragment.getMapAsync(this);
    }

    private String getCityName(LatLng latLng) {
        String myCity = "";
        Geocoder geocoder = new Geocoder(SearchLocation.this,Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude,latLng.longitude,1);
            String address = addresses.get(0).getAddressLine(0);
           // String city = addresses.get(0).getLocality();
            //String state = addresses.get(0).getAdminArea();
           // String zip = addresses.get(0).getPostalCode();
           // String country = addresses.get(0).getCountryName();
            myCity = address;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myCity;
    }

    private void setLocation(String s,String loc) {
        AlertDialog.Builder altdial = new AlertDialog.Builder(this);
        altdial.setMessage("Select the picked location?").setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       //yes clicked
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("KEY",s);
                        returnIntent.putExtra("KEY2",loc);
                        setResult(1,returnIntent);   // for the first argument, you could set Activity.RESULT_OK or your custom rescode too
                        finish();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //clicked no
                        dialog.cancel();
                    }
                });
        AlertDialog alert = altdial.create();
        alert.setTitle("Choose location");
        alert.show();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

    }

}