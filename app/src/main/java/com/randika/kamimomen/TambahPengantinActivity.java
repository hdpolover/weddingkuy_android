package com.randika.kamimomen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.randika.kamimomen.api.ApiClient;
import com.randika.kamimomen.api.ApiInterface;
import com.randika.kamimomen.api.responses.BaseResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahPengantinActivity extends AppCompatActivity {

    MaterialButton tambahPengantinBtn;
    EditText namaPengantinEt;

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_pengantin);

        Toolbar toolbar = findViewById(R.id.toolbarMy);

        setSupportActionBar(toolbar);
        setTitle("Tambah Pengantin Baru");

        apiInterface = ApiClient.getClient();

        tambahPengantinBtn = findViewById(R.id.tambahPengantinBtn);
        namaPengantinEt = findViewById(R.id.namaEt);

        tambahPengantinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean cekKode = true;

                if (namaPengantinEt.getText().toString().isEmpty()) {
                    namaPengantinEt.setError("Mohon isi data berikut");
                    cekKode = false;
                }

                if (cekKode) {
                    tambahPengantin();
                }
            }
        });
    }

    void tambahPengantin() {
        apiInterface.tambahPengantin(
                namaPengantinEt.getText().toString().trim()
        ).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response != null) {
                    if (response.body().status) {
                        Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("login", t.getMessage());
            }
        });
    }
}