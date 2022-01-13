package com.randika.kamimomen.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.randika.kamimomen.R;
import com.randika.kamimomen.api.ApiInterface;
import com.randika.kamimomen.api.responses.BukuTamuResponse;

import java.util.List;

public class BukuTamuAdapter extends RecyclerView.Adapter<BukuTamuAdapter.ViewHolder> {
    private static List<BukuTamuResponse.BukuTamuModel> list;
    Context context;

    public BukuTamuAdapter(List<BukuTamuResponse.BukuTamuModel> list, Context context) {
        BukuTamuAdapter.list = list;
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
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tamu, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, list.get(position).nama, Toast.LENGTH_SHORT).show();
            }
        });

        holder.namaTv.setText(list.get(position).getNama());
        holder.tglScanTv.setText(list.get(position).getTglScan());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView namaTv, tglScanTv;
        private MaterialCardView cv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            namaTv = itemView.findViewById(R.id.namaTamuTv);
            tglScanTv = itemView.findViewById(R.id.tglScanTv);
            cv = itemView.findViewById(R.id.tamuCv);
        }
    }
}
