package com.randika.kamimomen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

public class TentangAplikasiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tentang_aplikasi);

        Toolbar toolbar = findViewById(R.id.toolbarMy);

        setSupportActionBar(toolbar);
        setTitle("Tentang Aplikasi");
    }
}