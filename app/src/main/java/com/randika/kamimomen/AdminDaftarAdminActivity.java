package com.randika.kamimomen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.randika.kamimomen.adapters.AdminAdapter;
import com.randika.kamimomen.adapters.LokasiAdapter;
import com.randika.kamimomen.api.ApiClient;
import com.randika.kamimomen.api.ApiInterface;
import com.randika.kamimomen.api.responses.AdminResponse;
import com.randika.kamimomen.api.responses.LokasiResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminDaftarAdminActivity extends AppCompatActivity {

    ApiInterface apiInterface;
    MaterialButton tambahBtn;

    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_daftar_admin);

        Toolbar toolbar = findViewById(R.id.toolbarMy);

        setSupportActionBar(toolbar);
        setTitle("Daftar Admin");

        apiInterface = ApiClient.getClient();

        tambahBtn = findViewById(R.id.tambahBtn);
        rv = findViewById(R.id.rv);

        tambahBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminDaftarAdminActivity.this, AdminTambahAdminActivity.class));
            }
        });

        getAdmin();
    }

    private void getAdmin() {
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        apiInterface.getAdmin().enqueue(new Callback<AdminResponse>() {
            @Override
            public void onResponse(Call<AdminResponse> call, Response<AdminResponse> response) {
                if (response.body().status) {
                    List<AdminResponse.AdminModel> list = new ArrayList<>();

                    list.addAll(response.body().data);

                    rv.setAdapter(new AdminAdapter(list, getApplicationContext()));
                }
            }

            @Override
            public void onFailure(Call<AdminResponse> call, Throwable t) {
                Toast.makeText(AdminDaftarAdminActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}