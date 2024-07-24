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
import com.example.doan_1.model.SanPham;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ViewHolder> {
     Context context;
     ArrayList<SanPham> arrSanPham;
    ProductCallback productCallBack;
    FirebaseUser currentUser;



    public SanPhamAdapter(Context context, ArrayList<SanPham> arr_SanPham,ProductCallback productCallBack) {
        this.context = context;
        this.arrSanPham = arr_SanPham;
        this.productCallBack=productCallBack;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View viewSanPham = layoutInflater.inflate(R.layout.items, parent, false);
        return new ViewHolder(viewSanPham);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViewHolder myViewHolder = (ViewHolder) holder;
        SanPham sp = arrSanPham.get(position);
        // Sử dụng Glide để tải hình ảnh từ URL
        Glide.with(context)
                .load(sp.getSurl())
                .placeholder(R.drawable.sn_5) // Ảnh placeholder trong quá trình tải
                .error(R.drawable.img_no_image) // Ảnh hiển thị khi xảy ra lỗi
                .into(holder.ivHinh);
        if (sp != null) {
            holder.txtTenSP.setText("" + sp.getTen_sp());
        }

        //holder.txtTenSP.setText("" + sp.getTen_sp());
        DecimalFormat df = new DecimalFormat("#,### VNĐ");
        String giaFormatted = df.format(sp.getGia());
        holder.txtGiasp.setText(df.format(sp.getGia()));
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        holder.itemView.setOnClickListener(view -> productCallBack.onItemClick(sp.getTen_sp(),sp.getGia(),sp.getSurl(), sp.getMo_ta()));



    }

    @Override
    public int getItemCount() {
        return arrSanPham.size();
    }


       /* public void updateList(ArrayList<SanPham> filteredList) {
            // Cập nhật dữ liệu adapter với danh sách đã lọc
            arrSanPham.clear();
            arrSanPham.addAll(filteredList);
            notifyDataSetChanged();
        }*/


    public interface ProductCallback {

        void onItemClick(String ten, int price, String anh, String mota);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
       ImageView ivHinh;
        TextView txtTenSP, txtGiasp;
        CardView redcard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivHinh = itemView.findViewById(R.id.ivHinh);
            txtTenSP = itemView.findViewById(R.id.txtTenSP);
            txtGiasp = itemView.findViewById(R.id.txtGiasp);
            redcard=itemView.findViewById(R.id.card);
        }
    }
    public void add(SanPham sp) {
        arrSanPham.add(sp);
        notifyDataSetChanged();
    }
}
