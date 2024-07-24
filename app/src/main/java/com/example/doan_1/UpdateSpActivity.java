package com.example.doan_1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.doan_1.adapter.ProductsAdapter;
import com.example.doan_1.databinding.ActivityAddProductsBinding;
import com.example.doan_1.model.Products;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UpdateSpActivity extends AppCompatActivity implements ProductsAdapter.ProductCallback1 {

    TextView txtten_sp1, txtgia1, txtso_luong1, txtmo_ta1, txtsurl1, txtmaSP1;
    ImageView img_update1;
    Button btnUpdate, btnBack;
    Products pr=new Products();
    DatabaseReference databaseReference;
    Uri uri;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_sp);

        txtmaSP1 = findViewById(R.id.txtmaSP);
        txtten_sp1 = findViewById(R.id.txtten_sp);
        txtgia1 = findViewById(R.id.txtgia);
        txtso_luong1 = findViewById(R.id.txtso_luong);
        txtmo_ta1 = findViewById(R.id.txtmo_ta);
        txtsurl1 = findViewById(R.id.txtsurl);
        img_update1 = findViewById(R.id.img1);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnBack = findViewById(R.id.btnBack);

        String id = getIntent().getStringExtra("id");
        txtmaSP1.setText(id +"");
        String ten_sp = getIntent().getStringExtra("ten_sp");
        txtten_sp1.setText(ten_sp +"");
        pr.setTen_sp(ten_sp);
        Integer gia = getIntent().getIntExtra("gia", 0);
        DecimalFormat df = new DecimalFormat("#,### VNĐ");
        txtgia1.setText(gia +"");
        pr.setGia(gia);
        Integer so_luong = getIntent().getIntExtra("so_luong", 0);
        txtso_luong1.setText(so_luong +"");
        pr.setSo_luong(so_luong);
        String mo_ta = getIntent().getStringExtra("mo_ta");
        txtmo_ta1.setText(mo_ta +"");
        pr.setMo_ta(mo_ta);
        String surl = getIntent().getStringExtra("surl");
        txtsurl1.setText(surl +"");
        pr.setSurl(surl);

        surl = getIntent().getStringExtra("surl");
        Glide.with(getApplicationContext())
                .load(surl)
                .placeholder(R.drawable.img_no_image)
                .into(img_update1);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = txtmaSP1.getText().toString();
                String ten_sp = txtten_sp1.getText().toString();
                Integer gia = Integer.valueOf(txtgia1.getText().toString());
                Integer so_luong = Integer.valueOf(txtso_luong1.getText().toString());
                String mo_ta = txtmo_ta1.getText().toString();
                String surl = txtsurl1.getText().toString();
                updateData(id, ten_sp, gia, so_luong, mo_ta, surl);
                Intent intent = new Intent(UpdateSpActivity.this, AdProActivity.class);
                startActivity(intent);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UpdateSpActivity.this, AdProActivity.class);
                startActivity(i);
            }
        });
    }

    private void updateData(String id, String ten_sp, Integer gia, Integer so_luong, String mo_ta, String surl) {
        HashMap Product = new HashMap();
        Product.put("id", id);
        Product.put("ten_sp", ten_sp);
        Product.put("gia", gia);
        Product.put("so_luong", so_luong);
        Product.put("mo_ta", mo_ta);
        Product.put("surl", surl);

        databaseReference = FirebaseDatabase.getInstance().getReference("products");
        databaseReference.child(id).updateChildren(Product).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()){
                    txtmaSP1.setText("");
                    txtten_sp1.setText("");
                    txtgia1.setText(gia+"");
                    txtso_luong1.setText(so_luong+"");
                    txtmo_ta1.setText("");
                    txtsurl1.setText("");


                    Toast.makeText(UpdateSpActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(UpdateSpActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    public void onbtnEditClick(String maSP, String name, int Gia, int soluong, String hinhanh, String moTa){
        Intent i = new Intent(this, UpdateSpActivity.class);
        i.putExtra("id", maSP);
        i.putExtra("ten_sp", name);
        i.putExtra("gia", Gia);
        i.putExtra("so_luong", soluong);
        i.putExtra("mo_ta", moTa);
        i.putExtra("surl", hinhanh);
        startActivity(i);
    }


}