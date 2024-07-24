package com.example.doan_1.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.doan_1.R;
import com.example.doan_1.model.Voucher;

public class VoucherAdapter extends ArrayAdapter<Voucher> {
    Activity context;
    int resouce;
    public VoucherAdapter(@NonNull Activity context, int resource) {
        super(context, resource);
        this.context=context;
        this.resouce=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater=this.context.getLayoutInflater();
        View customView=layoutInflater.inflate(this.resouce,null);
        ImageView ivvoucher=customView.findViewById(R.id.ivvoucher);
        TextView txtgiamgia1=customView.findViewById(R.id.txtgiamgia1);
        TextView txtsoluong=customView.findViewById(R.id.txtsoluong);
        Voucher vc=getItem(position);
        ivvoucher.setImageResource(vc.getIvvoucher());
        txtgiamgia1.setText(vc.getGiamgia1()+"");
        txtsoluong.setText(vc.getSoluong()+"");
        return customView;

    }
}
