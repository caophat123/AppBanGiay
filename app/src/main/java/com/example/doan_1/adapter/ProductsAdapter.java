package com.example.doan_1.adapter;



import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doan_1.AdProActivity;
import com.example.doan_1.R;
import com.example.doan_1.model.Products;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductsAdapter extends FirebaseRecyclerAdapter<Products,ProductsAdapter.myViewProduct> {
//private Context context;
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    ProductCallback1 productCallback1;
    DatabaseReference reference;
    public ProductsAdapter(@NonNull FirebaseRecyclerOptions<Products> options, AdProActivity adProActivity, ProductCallback1 productCallback1) {
        super(options);
        this.productCallback1=productCallback1;
    }


    @Override
    public void onBindViewHolder(@NonNull myViewProduct holder,int position, @NonNull Products model) {

        holder.ten_sp.setText(""+model.getTen_sp());
        DecimalFormat df =new DecimalFormat("#,### VNĐ");
        String giafm= df.format(model.getGia());
        holder.gia.setText(df.format(model.getGia()));
        holder.so_luong.setText(""+model.getSo_luong());
        holder.mo_ta.setText("" + model.getMo_ta());
        holder.ma_sp.setText("" + model.getId());


        Glide.with(holder.img.getContext())
                .load(model.getSurl())
                .placeholder(com.google.android.gms.base.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.google.android.gms.base.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.img);

        holder.btnEdit.setOnClickListener(view -> productCallback1.onbtnEditClick(model.getId(), model.getTen_sp(), model.getGia(), model.getSo_luong(), model.getMo_ta(), model.getSurl()));

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.ma_sp.getContext());
                builder.setTitle("Bạn có chắc chắn?");
                builder.setMessage("Xóa dữ liệu không thể hoàn tác");

                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reference = FirebaseDatabase.getInstance().getReference("products");
                        reference.child(model.getId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(holder.ma_sp.getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                                    holder.ma_sp.setText("");
                                }else {
                                    Toast.makeText(holder.ma_sp.getContext(), "Xóa thất bại", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.ma_sp.getContext(), "Đã hủy", Toast.LENGTH_LONG).show();
                    }
                });
                builder.show();
            }
        });
    }

    @NonNull
    @Override
    public myViewProduct onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adpro_item, parent, false);
        return new myViewProduct(view);
    }

    class myViewProduct extends RecyclerView.ViewHolder{
        Button btnEdit, btnDelete;
        CircleImageView img;
        TextView ten_sp, gia, so_luong, mo_ta, ma_sp;
        CardView redcard;

        public myViewProduct(@NonNull View itemView) {
            super(itemView);

            img =  itemView.findViewById(R.id.img1);
            ten_sp =  itemView.findViewById(R.id.ten_sp);
            gia =  itemView.findViewById(R.id.gia);
            so_luong =  itemView.findViewById(R.id.so_luong);
            mo_ta =  itemView.findViewById(R.id.mo_ta);
            ma_sp = itemView.findViewById(R.id.ma_sp);
            redcard=itemView.findViewById(R.id.card2);

            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }



    public interface ProductCallback1 {

        void onbtnEditClick(String id, String ten_sp, int gia, int so_luong, String mo_ta ,String surl);

    }

}
