package com.randika.kamimomen;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.button.MaterialButton;
import com.randika.kamimomen.api.ApiClient;
import com.randika.kamimomen.api.ApiInterface;
import com.randika.kamimomen.api.responses.BaseResponse;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminTambahLokasiActivity extends AppCompatActivity {

    ApiInterface apiInterface;
    MaterialButton simpanBtn, pilihFotoBtn;

    EditText namaEt, alamatEt, igEt, waEt, latEt, longEt;
    Spinner spinner;
    ImageView gambarIv;

    Uri foto;

    int selectedItem = 0;
    ArrayList<String> kategoriList = new ArrayList<>();

    ProgressDialog progressDialog;

    String jenis = "Wedding Organizer (WO)";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_tambah_lokasi);

        Toolbar toolbar = findViewById(R.id.toolbarMy);

        setSupportActionBar(toolbar);
        setTitle("Tambah Lokasi");

        apiInterface = ApiClient.getClient();

        simpanBtn = findViewById(R.id.simpanBtn);
        pilihFotoBtn = findViewById(R.id.pilihFotoBtn);
        namaEt = findViewById(R.id.namaEt);
        alamatEt = findViewById(R.id.alamatEt);
        igEt = findViewById(R.id.igEt);
        waEt = findViewById(R.id.noWaEt);
        spinner = findViewById(R.id.spinner);
        gambarIv = findViewById(R.id.gambatIv);
        latEt = findViewById(R.id.latitudeEt);
        longEt = findViewById(R.id.longitudeEt);

        apiInterface = ApiClient.getClient();

        kategoriList.add("Wedding Organizer (WO)");
        kategoriList.add("Fotografi");
        kategoriList.add("Souvenir");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AdminTambahLokasiActivity.this, android.R.layout.simple_spinner_item, kategoriList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedItem = i;

                if (i == 0) {
                    jenis = "Wedding Organizer (WO)";
                } else if (i == 1) {
                    jenis = "Fotografi";
                } else {
                    jenis = "Souvenir";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        pilihFotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(AdminTambahLokasiActivity.this)
                        .compress(3000)
                        .start();
            }
        });

        simpanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkData();
            }
        });


    }

    private void checkData() {
        String nama = namaEt.getText().toString().trim();
        String alamat = alamatEt.getText().toString().trim();
        String wa = waEt.getText().toString().trim();
        String ig = igEt.getText().toString().trim();
        String lat = latEt.getText().toString().trim();
        String longi = longEt.getText().toString().trim();

        if (nama.isEmpty() || alamat.isEmpty() || wa.isEmpty() || ig.isEmpty() || lat.isEmpty() || longi.isEmpty() || foto == null) {
            Toast.makeText(AdminTambahLokasiActivity.this, "Ada field yang masih kosong. Silakan isi terlebih dahulu.", Toast.LENGTH_LONG).show();
        } else {
            progressDialog = new ProgressDialog(AdminTambahLokasiActivity.this);
            progressDialog.setCancelable(false);
            progressDialog.setTitle("Pesan");
            progressDialog.setMessage("Mohon tunggu sebentar...");
            progressDialog.show();

            RequestBody namaR = RequestBody.create(MediaType.parse("text/plain"), nama);
            RequestBody alamatR = RequestBody.create(MediaType.parse("text/plain"), alamat);
            RequestBody waR = RequestBody.create(MediaType.parse("text/plain"), wa);
            RequestBody igR = RequestBody.create(MediaType.parse("text/plain"), ig);
            RequestBody jeniR = RequestBody.create(MediaType.parse("text/plain"), jenis);

            RequestBody latR = RequestBody.create(MediaType.parse("text/plain"), lat);
            RequestBody longR = RequestBody.create(MediaType.parse("text/plain"), longi);

            //image
            File file = new File(foto.getPath());
            RequestBody reqFile =  RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part f =  MultipartBody.Part.createFormData("image", file.getName(), reqFile);

            apiInterface.tambahLokasi(
                    namaR,
                    jeniR,
                    alamatR,
                    igR,
                    waR,
                    latR,
                    longR,
                    f
            ).enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    if (response != null) {
                        if (response.body().status) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }

                            onBackPressed();
                            Toast.makeText(AdminTambahLokasiActivity.this, "Tambah Lokasi berhasil.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(AdminTambahLokasiActivity.this, "Terjadi kesalahan.", Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    Log.e("daftar", t.getMessage());
                }
            });
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Uri fileUri = data.getData();

            foto = fileUri;
            gambarIv.setImageURI(fileUri);
        }
    }
}