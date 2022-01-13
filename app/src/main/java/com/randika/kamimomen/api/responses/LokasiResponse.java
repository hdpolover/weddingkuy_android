package com.randika.kamimomen.api.responses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LokasiResponse extends BaseResponse{
    @SerializedName("data")
    public List<LokasiModel> data;

    public static class LokasiModel {
        @SerializedName("id_tempat")
        public String idLokasi;

        @SerializedName("nama")
        public String nama;

        @SerializedName("jenis")
        public String jenis;

        @SerializedName("alamat")
        public String alamat;

        @SerializedName("akun_ig")
        public String ig;

        @SerializedName("no_wa")
        public String wa;

        @SerializedName("foto")
        public String foto;

        @SerializedName("latitude")
        public String lat;

        @SerializedName("longitude")
        public String longi;

        public LokasiModel(String idLokasi, String nama, String jenis, String alamat, String ig, String wa, String foto, String lat, String longi) {
            this.idLokasi = idLokasi;
            this.nama = nama;
            this.jenis = jenis;
            this.alamat = alamat;
            this.ig = ig;
            this.wa = wa;
            this.foto = foto;
            this.lat = lat;
            this.longi = longi;
        }

        public String getIdLokasi() {
            return idLokasi;
        }

        public void setIdLokasi(String idLokasi) {
            this.idLokasi = idLokasi;
        }

        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        public String getJenis() {
            return jenis;
        }

        public void setJenis(String jenis) {
            this.jenis = jenis;
        }

        public String getAlamat() {
            return alamat;
        }

        public void setAlamat(String alamat) {
            this.alamat = alamat;
        }

        public String getIg() {
            return ig;
        }

        public void setIg(String ig) {
            this.ig = ig;
        }

        public String getWa() {
            return wa;
        }

        public void setWa(String wa) {
            this.wa = wa;
        }

        public String getFoto() {
            return foto;
        }

        public void setFoto(String foto) {
            this.foto = foto;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLongi() {
            return longi;
        }

        public void setLongi(String longi) {
            this.longi = longi;
        }
    }
}
