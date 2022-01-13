package com.randika.kamimomen.api;

import com.randika.kamimomen.api.responses.AdminResponse;
import com.randika.kamimomen.api.responses.BaseResponse;
import com.randika.kamimomen.api.responses.BukuTamuResponse;
import com.randika.kamimomen.api.responses.LokasiResponse;
import com.randika.kamimomen.api.responses.PengantinResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("admin/tambah_admin")
    Call<BaseResponse> tambahAdmin(
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("admin/tambah_pengantin")
    Call<BaseResponse> tambahPengantin(
            @Field("nama") String nama
    );

    @GET("admin/login")
    Call<AdminResponse> loginAdmin(
            @Query("username") String username,
            @Query("password") String password
    );

    @GET("admin")
    Call<AdminResponse> getAdmin();

    @GET("pengantin/login")
    Call<PengantinResponse> loginPengantin(
            @Query("id_pengantin") String idPengantin
    );

    @FormUrlEncoded
    @POST("buku_tamu/simpan_tamu")
    Call<BaseResponse> simpanTamu(
            @Field("id_pengantin") String idPengantin,
            @Field("nama") String nama
    );

    @GET("buku_tamu/riwayat")
    Call<BukuTamuResponse> riwayat(
            @Query("id_pengantin") String idPengantin
    );

    @GET("pengantin")
    Call<PengantinResponse> daftarPengantin();

    @Multipart
    @POST("lokasi/tambah")
    Call<BaseResponse> tambahLokasi(
            @Part("nama") RequestBody idPengguna,
            @Part("jenis") RequestBody nama,
            @Part("alamat") RequestBody alamat,
            @Part("akun_ig") RequestBody akunIg,
            @Part("no_wa") RequestBody noWa,
            @Part("latitude") RequestBody latitude,
            @Part("longitude") RequestBody longitude,
            @Part MultipartBody.Part image
    );

    @GET("lokasi")
    Call<LokasiResponse> getLokasi();

    @GET("lokasi/jenis")
    Call<LokasiResponse> getLokasiJenis(
            @Query("jenis") String jenis
    );
}
