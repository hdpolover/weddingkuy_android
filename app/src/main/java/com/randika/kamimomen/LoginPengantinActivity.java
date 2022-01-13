package com.randika.kamimomen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.randika.kamimomen.api.ApiClient;
import com.randika.kamimomen.api.ApiInterface;
import com.randika.kamimomen.api.responses.AdminResponse;
import com.randika.kamimomen.api.responses.PengantinResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPengantinActivity extends AppCompatActivity {

    MaterialButton loginBtn;
    EditText kodeEt;

    ApiInterface apiInterface;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_pengantin);

        apiInterface = ApiClient.getClient();

        loginBtn = findViewById(R.id.loginBtn);
        kodeEt = findViewById(R.id.kodeEt);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean cekKode = true;

                if (kodeEt.getText().toString().isEmpty()) {
                    kodeEt.setError("Mohon isi data berikut");
                    cekKode = false;
                }

                if (cekKode) {
                    progressDialog = new ProgressDialog(LoginPengantinActivity.this);
                    progressDialog.setCancelable(false);
                    progressDialog.setTitle("Pesan");
                    progressDialog.setMessage("Mohon tunggu sebentar...");
                    progressDialog.show();

                    loginPengantin();
                }

            }
        });
    }

    void loginPengantin() {
        apiInterface.loginPengantin(
                kodeEt.getText().toString().trim()
        ).enqueue(new Callback<PengantinResponse>() {
            @Override
            public void onResponse(Call<PengantinResponse> call, Response<PengantinResponse> response) {
                if (response != null) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }

                    if (response.body().status) {
                        Toast.makeText(getApplicationContext(), "Selamat datang, " + response.body().data.get(0).nama, Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(LoginPengantinActivity.this, BerandaPengantinActivity.class);
                        i.putExtra("id_pengantin", response.body().data.get(0).idPengantin);
                        startActivity(i);
                    finish();
                    } else {

                        Toast.makeText(getApplicationContext(), "Kode salah. Silakan coba lagi.", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<PengantinResponse> call, Throwable t) {
                Log.e("login", t.getMessage());
            }
        });
    }
}