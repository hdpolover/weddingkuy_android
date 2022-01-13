package com.randika.kamimomen.api.responses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AdminResponse extends BaseResponse{
    @SerializedName("data")
    public List<AdminModel> data;

    public static class AdminModel {
        @SerializedName("id_admin")
        public String idAdmin;

        @SerializedName("username")
        public String username;

        @SerializedName("password")
        public String password;

        public AdminModel(String idAdmin, String username, String password) {
            this.idAdmin = idAdmin;
            this.username = username;
            this.password = password;
        }

        public String getIdAdmin() {
            return idAdmin;
        }

        public void setIdAdmin(String idAdmin) {
            this.idAdmin = idAdmin;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
