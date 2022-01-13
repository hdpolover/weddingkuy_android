package com.randika.kamimomen.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.randika.kamimomen.R;
import com.randika.kamimomen.adapters.LokasiAdapter;
import com.randika.kamimomen.api.ApiClient;
import com.randika.kamimomen.api.ApiInterface;
import com.randika.kamimomen.api.responses.LokasiResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SouvenirFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView rv;
    View view;
    ApiInterface apiInterface;

    String kategori = "Souvenir";

    LinearLayout noData;

    public SouvenirFragment() {
        // Required empty public constructor
    }

    public static SouvenirFragment newInstance(String param1, String param2) {
        SouvenirFragment fragment = new SouvenirFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sou_container, container, false);

        rv  = view.findViewById(R.id.rv);
        apiInterface = ApiClient.getClient();

        noData = view.findViewById(R.id.noDataLayout);

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        tampilProduk(kategori);

        return view;
    }

    private void tampilProduk(String kategori) {
        apiInterface.getLokasiJenis(kategori).enqueue(new Callback<LokasiResponse>() {
            @Override
            public void onResponse(Call<LokasiResponse> call, Response<LokasiResponse> response) {
                if (response.body().status) {
                    List<LokasiResponse.LokasiModel> list = new ArrayList<>();

                    list.addAll(response.body().data);

                    rv.setAdapter(new LokasiAdapter(list, getContext()));

                    if (list.isEmpty()) {
                        noData.setVisibility(View.VISIBLE);
                    } else {
                        noData.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<LokasiResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}