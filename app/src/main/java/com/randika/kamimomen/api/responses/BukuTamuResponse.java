package com.randika.kamimomen.api.responses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BukuTamuResponse extends BaseResponse{
    @SerializedName("data")
    public List<BukuTamuModel> data;

    public static class BukuTamuModel {
        @SerializedName("id_tamu")
        public String idTamu;

        @SerializedName("id_pengantin")
        public String idPengantin;

        @SerializedName("nama")
        public String nama;

        @SerializedName("tgl_scan")
        public String tglScan;

        public BukuTamuModel(String idTamu, String idPengantin, String nama, String tglScan) {
            this.idTamu = idTamu;
            this.idPengantin = idPengantin;
            this.nama = nama;
            this.tglScan = tglScan;
        }

        public String getIdTamu() {
            return idTamu;
        }

        public void setIdTamu(String idTamu) {
            this.idTamu = idTamu;
        }

        public String getIdPengantin() {
            return idPengantin;
        }

        public void setIdPengantin(String idPengantin) {
            this.idPengantin = idPengantin;
        }

        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        public String getTglScan() {
            return tglScan;
        }

        public void setTglScan(String tglScan) {
            this.tglScan = tglScan;
        }
    }
}
