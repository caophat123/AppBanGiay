package com.example.doan_1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_1.R;
import com.example.doan_1.model.Thongbao;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ThongBaoAdapter extends RecyclerView.Adapter<ThongBaoAdapter.ViewHolder> {

    private ArrayList<Thongbao> GList;
    private Context context;

    public ThongBaoAdapter(ArrayList<Thongbao> GList, Context context) {
        this.GList = GList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.sneaker_items, parent ,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViewHolder myViewHolder = (ViewHolder) holder;
        myViewHolder.TenThongBao.setText(GList.get(position).getTenthongbao());
        myViewHolder.NoiDungThongBao.setText(GList.get(position).getNoidung());
        myViewHolder.DateThongBao.setText(GList.get(position).getDatethongbao());
        myViewHolder.AvtThongBao.setText(GList.get(position).getHinh());

    }

    @Override
    public int getItemCount() {
        return GList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

//        CardView layoutThongBao;

//        TextView AvtThongBao;

        TextView TenThongBao,NoiDungThongBao,AvtThongBao,DateThongBao;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            layoutThongBao = itemView.findViewById(R.id.layoutThongBao);
            AvtThongBao = itemView.findViewById(R.id.AvtThongbao);
            TenThongBao = itemView.findViewById(R.id.TenThongBao);
            NoiDungThongBao = itemView.findViewById(R.id.NoiDungThongBao);
            DateThongBao = itemView.findViewById(R.id.DateThongBao);

        }
    }
}
