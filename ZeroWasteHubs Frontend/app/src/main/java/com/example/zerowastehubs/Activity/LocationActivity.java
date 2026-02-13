package com.example.zerowastehubs.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.zerowastehubs.Fragment.HomeFragment;
import com.example.zerowastehubs.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class LocationActivity extends FragmentActivity implements OnMapReadyCallback {

    ImageView backHome;
    private GoogleMap googleMap;
    private SearchView searchLocation;
    private TextView textUCurrent, tvCurrentAddress;

    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        backHome = findViewById(R.id.back_home);
        backHome.setOnClickListener(v -> {
            onBackPressed(); // simply return to previous screen
        });


        searchLocation = findViewById(R.id.searchLocation);
        textUCurrent = findViewById(R.id.textUCurrent);
        tvCurrentAddress = findViewById(R.id.tvCurrentAddress);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        // Map Initialization
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);

        // Current location button click
        textUCurrent.setOnClickListener(v -> {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
            }
        });

        // Search Location
        searchLocation.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchLocation(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });


    }
    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    if (location != null) {
                        try {
                            Geocoder geo = new Geocoder(this, Locale.getDefault());
                            List<Address> addresses = geo.getFromLocation(location.getLatitude(),
                                    location.getLongitude(), 1);


                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            googleMap.addMarker(new MarkerOptions().position(latLng).title("My Location"));
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));


//                            tvCurrentAddress.setText(addresses.get(0).getAddressLine(0));

                            String address =  addresses.get(0).getAddressLine(0);
                            textUCurrent.setText(address);
                            //transfer home fragment for locaation
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("address", address);
                            setResult(RESULT_OK, resultIntent);
                            finish();

//                            Intent intent = new Intent(LocationActivity.this, HomeFragment.class);
//                            intent.putExtra("address", address);
//                            setResult(RESULT_OK, intent);
//                            startActivity(intent);
//                            finish(); // close LocationActivity

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void searchLocation(String query) {
        try {
            Geocoder geocoder = new Geocoder(this);
            List<Address> addresses = geocoder.getFromLocationName(query, 1);

            if (addresses != null && !addresses.isEmpty()) {

                Address a = addresses.get(0);
                LatLng latLng = new LatLng(a.getLatitude(), a.getLongitude());

                googleMap.clear();
                googleMap.addMarker(new MarkerOptions().position(latLng).title(query));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

                 String address =  addresses.get(0).getAddressLine(0);
                textUCurrent.setText(address);
                //transfer home fragment for locaation

                Intent resultIntent = new Intent();
                resultIntent.putExtra("address", address);
                setResult(RESULT_OK, resultIntent);
                finish();

//                Intent intent = new Intent(LocationActivity.this, HomeFragment.class);
//                intent.putExtra("address", address);
//                setResult(RESULT_OK, intent);
//                startActivity(intent);
//                finish(); // close LocationActivity
            } else {
                Toast.makeText(this, "No result found", Toast.LENGTH_SHORT).show();
            }

        } catch (IOException e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap=googleMap;
    }
}