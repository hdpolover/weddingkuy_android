package com.randika.kamimomen.api.responses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PengantinResponse extends BaseResponse{
    @SerializedName("data")
    public List<PengantinModel> data;

    public static class PengantinModel {
        @SerializedName("id_pengantin")
        public String idPengantin;

        @SerializedName("nama")
        public String nama;

        public PengantinModel(String idPengantin, String nama) {
            this.idPengantin = idPengantin;
            this.nama = nama;
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
    }
}
