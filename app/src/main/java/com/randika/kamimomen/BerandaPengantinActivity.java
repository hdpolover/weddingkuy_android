package com.randika.kamimomen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.randika.kamimomen.adapters.BukuTamuAdapter;
import com.randika.kamimomen.api.ApiClient;
import com.randika.kamimomen.api.ApiInterface;
import com.randika.kamimomen.api.responses.BukuTamuResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BerandaPengantinActivity extends AppCompatActivity {

    FloatingActionButton scanFab;
    RecyclerView rv;

    String idPengantin;

    ApiInterface apiInterface;
    ArrayList<BukuTamuResponse.BukuTamuModel> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda_pengantin);

        Toolbar toolbar = findViewById(R.id.toolbarMy);
        rv = findViewById(R.id.rv);

        setSupportActionBar(toolbar);

        apiInterface = ApiClient.getClient();

        scanFab = findViewById(R.id.scanFab);

        idPengantin = getIntent().getStringExtra("id_pengantin");

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setHasFixedSize(true);

        getBukuTamu();

        scanFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BerandaPengantinActivity.this, QrScannerActivity.class);
                i.putExtra("id_pengantin", idPengantin);
                startActivity(i);
            }
        });
    }

    private void getBukuTamu() {
        apiInterface.riwayat(
                idPengantin
        ).enqueue(new Callback<BukuTamuResponse>() {
            @Override
            public void onResponse(Call<BukuTamuResponse> call, Response<BukuTamuResponse> response) {
                if (response.body().status) {
                    List<BukuTamuResponse.BukuTamuModel> list = new ArrayList<>();

                    list.addAll(response.body().data);
                    data.addAll(response.body().data);

                    rv.setAdapter(new BukuTamuAdapter(list, getApplicationContext()));
                }
            }

            @Override
            public void onFailure(Call<BukuTamuResponse> call, Throwable t) {
                Toast.makeText(BerandaPengantinActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_pengantin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.exportMenu:
                String url = getString(R.string.base_url) + "momenkami_api/buku_tamu/download?id_pengantin=" + idPengantin;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                return true;

            case R.id.keluarMenu:
                startActivity(new Intent(BerandaPengantinActivity.this, MainActivity.class));
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

}