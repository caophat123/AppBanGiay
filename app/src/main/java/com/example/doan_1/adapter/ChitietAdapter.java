package com.example.doan_1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doan_1.R;
import com.example.doan_1.model.Chitiet;

import java.util.ArrayList;

public class ChitietAdapter extends RecyclerView.Adapter<ChitietAdapter.ViewHolder> {
        Context context;
        ArrayList<Chitiet> arrChitiet;

public ChitietAdapter (Context context, ArrayList<Chitiet> arrChitiet) {
        this.context = context;
        this.arrChitiet = arrChitiet;
        }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View viewChiTiet = layoutInflater.inflate(R.layout.activity_chitiet, parent, false);
        return new ViewHolder(viewChiTiet);

    }

    @Override
public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Chitiet Ct = arrChitiet.get(position);

        // Sử dụng Glide để tải hình ảnh từ URL
        Glide.with(context)
        .load(Ct.getSurl())
        .placeholder(R.drawable.sn_5) // Ảnh placeholder trong quá trình tải
        .error(R.drawable.img_no_image) // Ảnh hiển thị khi xảy ra lỗi
        .into(holder.img_chitiet);

        holder.txtTenSP.setText("" + Ct.getTen_sp());
        holder.txtGiasp.setText("" + Ct.getGia());
        holder.txtMota.setText("" + Ct.getMo_ta());
        }

@Override
public int getItemCount() {
        return arrChitiet.size();
        }

public static class ViewHolder extends RecyclerView.ViewHolder {
    ImageView img_chitiet;
    TextView txtTenSP, txtGiasp,txtMota;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        img_chitiet = itemView.findViewById(R.id.img_chitiet);
        txtTenSP = itemView.findViewById(R.id.txttensp_chitiet);
        txtMota=itemView.findViewById(R.id.txtmota_chitiet);
        txtGiasp = itemView.findViewById(R.id.txtgiasp_chitiet);
    }
}
}
