package com.randika.kamimomen;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.randika.kamimomen.api.ApiClient;
import com.randika.kamimomen.api.ApiInterface;
import com.randika.kamimomen.api.responses.AdminResponse;
import com.randika.kamimomen.api.responses.BaseResponse;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QrScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    int MY_PERMISSIONS_REQUEST_CAMERA=0;

    ZXingScannerView scannerView;

    ApiInterface apiInterface;

    String idPengantin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);

        apiInterface = ApiClient.getClient();

        idPengantin = getIntent().getStringExtra("id_pengantin");
    }

    void simpanTamu(String nama) {
        apiInterface.simpanTamu(
                idPengantin,
                nama
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

    @Override
    public void handleResult(Result result) {
        String qrData = result.getText();
        final String qrDataForCall = qrData;

        String question = "Terima kasih telah hadir di acara pernikahan kami.";

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("HASIL SCAN KODE QR TAMU");

        final String finalQrData = qrData;

        alertDialog.setPositiveButton("SIMPAN",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        simpanTamu(finalQrData);

                        Toast.makeText(getApplicationContext(), "Berhasil scan tamu " + finalQrData, Toast.LENGTH_LONG).show();
                        dialog.cancel();
                        onResume();
                    }
                });

        AlertDialog alert = alertDialog.create();
        LayoutInflater inflater = getLayoutInflater();
        View qrDialog = inflater.inflate(R.layout.dialog_alert, null);
        alert.setView(qrDialog);

        TextView qrDataTv = qrDialog.findViewById(R.id.qrDataTv);
        qrDataTv.setText(qrData);

        TextView questionTv = qrDialog.findViewById(R.id.confirmTv);
        questionTv.setText(question);

        alert.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_CAMERA);
        }
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }
}