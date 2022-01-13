package com.randika.kamimomen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.randika.kamimomen.helpers.FetchURL;
import com.randika.kamimomen.helpers.TaskLoadedCallback;

public class NavLokasiActivity extends AppCompatActivity implements
        OnMapReadyCallback,
        TaskLoadedCallback {

    private GoogleMap mMap;
    MaterialButton navBtn, arahBtn;
    String nama;
    double lat, longi, lat1, longi1;

    private Polyline currentPolyline;
    private MarkerOptions place1, place2;

    int ACCESS_LOCATION_REQUEST_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_lokasi);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Toolbar toolbar = findViewById(R.id.toolbarMy);
        setSupportActionBar(toolbar);
        setTitle("Navigasi Lokasi");

        nama = getIntent().getStringExtra("nama");
        lat = Double.parseDouble(getIntent().getStringExtra("lat"));
        longi = Double.parseDouble(getIntent().getStringExtra("longi"));
        lat1 = Double.parseDouble(getIntent().getStringExtra("lat1"));
        longi1 = Double.parseDouble(getIntent().getStringExtra("longi1"));

        place1 = new MarkerOptions().position(new LatLng(lat1, longi1)).title("Lokasi Saya");
        place2 = new MarkerOptions().position(new LatLng(lat, longi)).title(nama);

        navBtn = findViewById(R.id.navBtn);
        arahBtn = findViewById(R.id.arahBtn);

        navBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + lat + "," + longi + "&mode=1"));
                i.setPackage("com.google.android.apps.maps");

                if (i.resolveActivity(getPackageManager()) != null) {
                    startActivity(i);
                }
            }
        });

        arahBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FetchURL(NavLokasiActivity.this).execute(getUrl(place1.getPosition(), place2.getPosition(), "driving"), "driving");
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Marker ma = mMap.addMarker(place1);
        ma.showInfoWindow();

        Marker ma1 = mMap.addMarker(place2);
        ma1.showInfoWindow();

        LatLng sydney = new LatLng(lat, longi);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setMinZoomPreference(13.0f);
    }


    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }
}