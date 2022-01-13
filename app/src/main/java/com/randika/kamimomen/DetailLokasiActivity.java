package com.randika.kamimomen;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class DetailLokasiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


// This contains the MapView in XML and needs to be called after the access token is configured.
        setContentView(R.layout.activity_detail_lokasi);

    }

}