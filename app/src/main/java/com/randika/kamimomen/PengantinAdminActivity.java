package com.randika.kamimomen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.randika.kamimomen.adapters.BukuTamuAdapter;
import com.randika.kamimomen.adapters.PengantinAdapter;
import com.randika.kamimomen.api.ApiClient;
import com.randika.kamimomen.api.ApiInterface;
import com.randika.kamimomen.api.responses.BukuTamuResponse;
import com.randika.kamimomen.api.responses.PengantinResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PengantinAdminActivity extends AppCompatActivity {

    FloatingActionButton tambahFab;
    RecyclerView rv;

    String idPengantin;

    ApiInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengantin_admin);

        Toolbar toolbar = findViewById(R.id.toolbarMy);
        rv = findViewById(R.id.rv);

        setSupportActionBar(toolbar);
        setTitle("Daftar Pengantin");

        apiInterface = ApiClient.getClient();

        tambahFab = findViewById(R.id.tambahFab);

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setHasFixedSize(true);

        getPengantin();

        tambahFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PengantinAdminActivity.this, TambahPengantinActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    void  getPengantin() {
        apiInterface.daftarPengantin().enqueue(new Callback<PengantinResponse>() {
            @Override
            public void onResponse(Call<PengantinResponse> call, Response<PengantinResponse> response) {
                if (response.body().status) {
                    List<PengantinResponse.PengantinModel> list = new ArrayList<>();

                    list.addAll(response.body().data);

                    rv.setAdapter(new PengantinAdapter(list, getApplicationContext()));
                }
            }

            @Override
            public void onFailure(Call<PengantinResponse> call, Throwable t) {
                Toast.makeText(PengantinAdminActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}