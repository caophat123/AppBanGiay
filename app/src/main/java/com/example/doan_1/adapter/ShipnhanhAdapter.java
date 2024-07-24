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
import com.example.doan_1.model.Shipnhanh;

public class ShipnhanhAdapter extends ArrayAdapter<Shipnhanh> {
    Activity context;
    int resouce;

    public ShipnhanhAdapter(@NonNull Activity context, int resource) {
        super(context, resource);
        this.context=context;
        this.resouce=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater=this.context.getLayoutInflater();
        View customView=layoutInflater.inflate(this.resouce,null);
        ImageView ivShipnhanh=customView.findViewById(R.id.ivShipnhanh);
        TextView txtHDon=customView.findViewById(R.id.txtHDon);
        TextView txtMa=customView.findViewById(R.id.txtMa);
        TextView txtSLuong=customView.findViewById(R.id.txtSLuong);
        TextView txtMagiam=customView.findViewById(R.id.txtMagiam);
        Shipnhanh sn=getItem(position);
        ivShipnhanh.setImageResource(sn.getHinhshipnhanh());
        txtMagiam.setText(sn.getMagiam());
        txtHDon.setText(sn.getHDon());
        txtMa.setText(sn.getMa()+"");
        txtSLuong.setText(sn.getSoluong()+"");
        return customView;
    }
}
