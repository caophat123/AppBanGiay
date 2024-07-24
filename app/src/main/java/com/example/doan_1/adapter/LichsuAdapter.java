package com.example.doan_1.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doan_1.ChitietActivity;
import com.example.doan_1.R;
import com.example.doan_1.model.Lichsu;
import com.example.doan_1.model.SanPham;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class LichsuAdapter extends RecyclerView.Adapter<LichsuAdapter.ViewHolder> {
    Context context;
    ArrayList<Lichsu> arrLichsu;



    public LichsuAdapter(Context context, ArrayList<Lichsu> arr_Lichsu) {
        this.context = context;
        this.arrLichsu = arr_Lichsu;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View viewLichsu = layoutInflater.inflate(R.layout.item_lichsu, parent, false);
        LichsuAdapter.ViewHolder viewHolderLS = new ViewHolder(viewLichsu);
        return viewHolderLS;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Lichsu sp = arrLichsu.get(position);
        if (sp != null) {
            // Sử dụng dữ liệu chỉ khi nó không null
            Glide.with(context)
                    .load(sp.getHinhLS())
                    .placeholder(R.drawable.sn_5)
                    .error(R.drawable.img_no_image)
                    .into(holder.ivHinh2);
            holder.txtTenSP2.setText("" + sp.getTenLS());
            holder.txtGiasp2.setText(String.valueOf(sp.getGiaLS()));
            holder.txtSoluong.setText(String.valueOf(sp.getSoluongLS()));
            holder.txtMa.setText(sp.getMaLS());
        }
    }

    @Override
    public int getItemCount() {
        return arrLichsu.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivHinh2;
        TextView txtTenSP2, txtGiasp2, txtSoluong, txtMa;
        CardView redcard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivHinh2 = itemView.findViewById(R.id.ivHinhDH);
            txtTenSP2 = itemView.findViewById(R.id.txtTenspDh);
            txtGiasp2 = itemView.findViewById(R.id.txtTongtienDH);
            txtSoluong=itemView.findViewById(R.id.txtSoluongSpDH);
            txtMa=itemView.findViewById(R.id.txtMaDH);
        }
    }
    public void add(Lichsu ls) {
        if (ls != null) {
            arrLichsu.add(ls);
            notifyDataSetChanged();
        }
    }

}
