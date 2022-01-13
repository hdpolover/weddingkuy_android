package com.randika.kamimomen.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.randika.kamimomen.ArahLokasiActivity;
import com.randika.kamimomen.DetailLokasiActivity;
import com.randika.kamimomen.R;
import com.randika.kamimomen.api.responses.BukuTamuResponse;
import com.randika.kamimomen.api.responses.LokasiResponse;

import java.util.List;

public class LokasiAdapter extends RecyclerView.Adapter<LokasiAdapter.ViewHolder> {
    private static List<LokasiResponse.LokasiModel> list;
    Context context;

    public LokasiAdapter(List<LokasiResponse.LokasiModel> list, Context context) {
        LokasiAdapter.list = list;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lokasi, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ArahLokasiActivity.class);
                i.putExtra("nama", list.get(position).getNama());
                i.putExtra("lat", list.get(position).getLat());
                i.putExtra("longi", list.get(position).getLongi());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });

        holder.namaTv.setText(list.get(position).getNama());
        holder.alamatTv.setText(list.get(position).getAlamat());
        holder.igTv.setText(list.get(position).getIg());
        holder.waTv.setText(list.get(position).getWa());

        Glide.with(context)
                .load(context.getString(R.string.base_url) + context.getString(R.string.lokasi_link) + list.get(position).getFoto())
                .centerCrop()
                .into(holder.fotoIv);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView namaTv, alamatTv, waTv, igTv;
        ImageView fotoIv;
        private MaterialCardView cv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            namaTv = itemView.findViewById(R.id.namaLokasiTv);
            alamatTv = itemView.findViewById(R.id.alamatLokasiTv);
            waTv = itemView.findViewById(R.id.waTv);
            igTv = itemView.findViewById(R.id.igTv);
            fotoIv = itemView.findViewById(R.id.gambarLokasiIv);
            cv = itemView.findViewById(R.id.tamuCv);
        }
    }
}
