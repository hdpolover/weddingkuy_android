package com.randika.kamimomen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

public class AdminTambahAdminActivity extends AppCompatActivity {

    ApiInterface apiInterface;
    MaterialButton simpanBtn;

    EditText usernameEt, passwordEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_tambah_admin);

        Toolbar toolbar = findViewById(R.id.toolbarMy);

        setSupportActionBar(toolbar);
        setTitle("Tambah Admin");

        apiInterface = ApiClient.getClient();

        simpanBtn = findViewById(R.id.simpanBtn);
        usernameEt = findViewById(R.id.usernameEt);
        passwordEt = findViewById(R.id.passwordEt);

        simpanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean cekKode = true;
                boolean cekPassword = true;

                if (usernameEt.getText().toString().isEmpty()) {
                    usernameEt.setError("Mohon isi data berikut");
                    cekKode = false;
                }

                if (passwordEt.getText().toString().isEmpty()) {
                    passwordEt.setError("Mohon isi data berikut");
                    cekPassword = false;
                }

                if (cekKode && cekPassword) {
                    tambahAdmin();
                }
            }
        });
    }

    void tambahAdmin() {
        apiInterface.tambahAdmin(
                usernameEt.getText().toString().trim(),
                passwordEt.getText().toString().trim()
        ).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response != null) {
                    if (response.body().status) {
                        Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_SHORT).show();
                        onBackPressed();
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