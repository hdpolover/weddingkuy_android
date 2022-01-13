package com.randika.kamimomen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.card.MaterialCardView;
import com.randika.kamimomen.api.ApiClient;
import com.randika.kamimomen.api.ApiInterface;

public class BerandaAdminActivity extends AppCompatActivity {

    MaterialCardView lokasiCv, pengantinCv, adminCv, logoutCv;

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda_admin);

        Toolbar toolbar = findViewById(R.id.toolbarMy);

        setSupportActionBar(toolbar);
        setTitle("Beranda Admin");

        apiInterface = ApiClient.getClient();

        lokasiCv = findViewById(R.id.lokasiCv);
        pengantinCv = findViewById(R.id.pengantinCv);
        adminCv = findViewById(R.id.adminCv);

        pengantinCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BerandaAdminActivity.this, PengantinAdminActivity.class));
            }
        });

        lokasiCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BerandaAdminActivity.this, AdminLokasiActivity.class));
            }
        });

        adminCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BerandaAdminActivity.this, AdminDaftarAdminActivity.class));
            }
        });
    }
}