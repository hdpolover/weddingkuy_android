package com.randika.kamimomen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.randika.kamimomen.adapters.LokasiAdapter;
import com.randika.kamimomen.api.ApiClient;
import com.randika.kamimomen.api.ApiInterface;
import com.randika.kamimomen.api.responses.LokasiResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminLokasiActivity extends AppCompatActivity {

    ApiInterface apiInterface;
    MaterialButton tambahBtn;

    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_lokasi);

        Toolbar toolbar = findViewById(R.id.toolbarMy);

        setSupportActionBar(toolbar);
        setTitle("Beranda Admin");

        apiInterface = ApiClient.getClient();

        tambahBtn = findViewById(R.id.tambahBtn);
        rv = findViewById(R.id.rv);

        tambahBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminLokasiActivity.this, AdminTambahLokasiActivity.class));
            }
        });

        getLokasi();

    }

    private void getLokasi() {
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        apiInterface.getLokasi().enqueue(new Callback<LokasiResponse>() {
            @Override
            public void onResponse(Call<LokasiResponse> call, Response<LokasiResponse> response) {
                if (response.body().status) {
                    List<LokasiResponse.LokasiModel> list = new ArrayList<>();

                    list.addAll(response.body().data);

                    rv.setAdapter(new LokasiAdapter(list, getApplicationContext()));
                }
            }

            @Override
            public void onFailure(Call<LokasiResponse> call, Throwable t) {
                Toast.makeText(AdminLokasiActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}