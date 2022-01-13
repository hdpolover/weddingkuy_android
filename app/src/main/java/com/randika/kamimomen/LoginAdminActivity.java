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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginAdminActivity extends AppCompatActivity {

    MaterialButton loginBtn;
    EditText usernameEt, passwordEt;

    ApiInterface apiInterface;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);

        apiInterface = ApiClient.getClient();

        loginBtn = findViewById(R.id.loginBtn);
        usernameEt = findViewById(R.id.usernameEt);
        passwordEt = findViewById(R.id.passwordEt);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean cekUsername = true;
                boolean cekPassword = true;

                if (usernameEt.getText().toString().isEmpty()) {
                    usernameEt.setError("Mohon isi data berikut");
                    cekUsername = false;
                }

                if (passwordEt.getText().toString().isEmpty()) {
                    passwordEt.setError("Mohon isi data berikut");
                    cekPassword = false;
                }

                if (cekUsername && cekPassword) {
                    progressDialog = new ProgressDialog(LoginAdminActivity.this);
                    progressDialog.setCancelable(false);
                    progressDialog.setTitle("Pesan");
                    progressDialog.setMessage("Mohon tunggu sebentar...");
                    progressDialog.show();

                    loginAdmin();
                }

            }
        });
    }

    void loginAdmin() {
        apiInterface.loginAdmin(
                usernameEt.getText().toString().trim(),
                passwordEt.getText().toString().trim()
        ).enqueue(new Callback<AdminResponse>() {
            @Override
            public void onResponse(Call<AdminResponse> call, Response<AdminResponse> response) {
                if (response != null) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }

                    if (response.body().status) {
                        Toast.makeText(getApplicationContext(), response.body().data.get(0).username, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginAdminActivity.this, BerandaAdminActivity.class));
                    finish();
                    } else {

                        Toast.makeText(getApplicationContext(), "Username atau password salah. Silakan coba lagi.", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AdminResponse> call, Throwable t) {
                Log.e("login", t.getMessage());
            }
        });
    }
}