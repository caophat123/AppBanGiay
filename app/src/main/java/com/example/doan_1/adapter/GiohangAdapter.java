package com.example.doan_1.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doan_1.R;
import com.example.doan_1.model.GioHang;
import com.example.doan_1.model.SanPham;
import com.example.doan_1.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.ArrayList;



public class GiohangAdapter extends RecyclerView.Adapter<GiohangAdapter.ViewHolder> {
    private DatabaseReference addUser = FirebaseDatabase.getInstance().getReference("User");

    private DatabaseReference GhOder = FirebaseDatabase.getInstance().getReference("GioHang");
   // Integer numberOder = 1;

    Activity context;
    ArrayList<GioHang> arr_GioHang;
    String idGH = addUser.push().getKey();



    public GiohangAdapter(Activity context, ArrayList<GioHang>arr_GioHang) {
        this.context = context;
        this.arr_GioHang = arr_GioHang;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View viewGioHang = layoutInflater.inflate(R.layout.items_giohang, parent, false);
        ViewHolder viewHolderGH = new ViewHolder(viewGioHang);
        return viewHolderGH;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GioHang gioHang = arr_GioHang.get(position);
        holder.item_giohang_tensp.setText(gioHang.getTen());

        String giaFormatted = gioHang.getGia().replaceAll("\\D+", ""); // Lấy ra toàn bộ các ký tự số trong chuỗi
        int gia = Integer.parseInt(giaFormatted);
        // Đặt giá sản phẩm vào TextView
        holder.item_giohang_gia.setText(gioHang.getGia());
        Glide.with(context)
                .load(gioHang.getHinh())
                .placeholder(R.drawable.sn_5) // Ảnh placeholder trong quá trình tải
                .error(R.drawable.img_no_image) // Ảnh hiển thị khi xảy ra lỗi
                .into(holder.item_giohang_image);


        holder.icHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Cảnh báo");
                builder.setIcon(R.drawable.ic_warning);
                builder.setMessage("Bạn có chắc chắn muốn xoá khỏi giỏ hàng '" +
                        arr_GioHang.get(holder.getAdapterPosition()).getTen() + "' không?");

                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String id = gioHang.getId();
                        GhOder.child(id).removeValue();
                        arr_GioHang.clear();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }

        });

        holder.ivminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int soluong = gioHang.getSoluong();
                if (soluong > 1) {
                    soluong--;
                    gioHang.setSoluong(soluong);
                    notifyDataSetChanged();
                    updateTotalPrice(holder, soluong, gia);
                }
                holder.item_giohang_soluong.setText(String.valueOf(soluong));
            }
        });
        holder.ivplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int soluong = gioHang.getSoluong();
                soluong++;
                gioHang.setSoluong(soluong);
                holder.item_giohang_soluong.setText(String.valueOf(soluong));
                notifyDataSetChanged();
                updateTotalPrice(holder, soluong, gia);

            }

        });


        int tongTien = gia * gioHang.getSoluong();
        DecimalFormat df = new DecimalFormat("#,### VNĐ");
        String giaFormatted2 = df.format(tongTien);
        holder.tongtien.setText(String.valueOf(df.format(tongTien)));



    }


    private void updateTotalPrice(ViewHolder holder, int soluong, int gia) {
        int totalPrice = soluong * gia;
        DecimalFormat df = new DecimalFormat("#,### VNĐ");
        String giaFormatted = df.format(totalPrice);
        holder.tongtien.setText(String.valueOf(df.format(totalPrice)));
    }





    @Override
    public int getItemCount() {
        return arr_GioHang.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView item_giohang_image, ivminus,ivplus;
        TextView item_giohang_tensp, item_giohang_gia, item_giohang_soluong,tongtien,icHuy;
        Button btnthem,btnThanhToan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_giohang_image = itemView.findViewById(R.id.item_giohang_image);
            item_giohang_tensp = itemView.findViewById(R.id.item_giohang_tensp);
            item_giohang_gia = itemView.findViewById(R.id.item_giohang_gia);
            item_giohang_soluong=itemView.findViewById(R.id.item_giohang_soluong);
            btnthem=itemView.findViewById(R.id.btnthemvaogiohang);
            ivminus=itemView.findViewById(R.id.item_giohang_tru);
            ivplus= itemView.findViewById(R.id.item_giohang_cong);
            tongtien=itemView.findViewById(R.id.item_giohang_tongtien);
            icHuy=itemView.findViewById(R.id.tvHuy);
        }
    }

    public void add(GioHang sp) {
        arr_GioHang.add(sp);
        notifyDataSetChanged();
    }
}
