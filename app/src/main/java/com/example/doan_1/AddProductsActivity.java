package com.example.doan_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.doan_1.databinding.ActivityAddProductsBinding;
import com.example.doan_1.model.Products;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.auth.User;

import java.text.DecimalFormat;

public class AddProductsActivity extends AppCompatActivity {

    ActivityAddProductsBinding binding;
    String ten_sp,  mo_ta, surl, id;
    Integer gia, so_luong;
    FirebaseDatabase db;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddProductsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //setContentView(R.layout.activity_add_products);

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                id = binding.edtMaSP.getText().toString();
                ten_sp = binding.edtTenSP.getText().toString();
                gia = Integer.valueOf(binding.edtGiaSP.getText().toString());
                so_luong = Integer.valueOf(binding.edtSoLuong.getText().toString());
                mo_ta = binding.edtDetail.getText().toString();
                surl = binding.edtSurl.getText().toString();

                Products products = new Products(id, ten_sp, gia, so_luong, mo_ta, surl);
                db = FirebaseDatabase.getInstance();
                reference = db.getReference("products");
                reference.child(id).setValue(products).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        binding.edtMaSP.setText("");
                        binding.edtTenSP.setText("");
                        DecimalFormat df= new DecimalFormat();
                        binding.edtGiaSP.setText(df.format(gia) +"");
                        binding.edtSoLuong.setText(so_luong+ "");
                        binding.edtDetail.setText("");
                        binding.edtSurl.setText("");

                        Toast.makeText(AddProductsActivity.this, "Thêm sản phẩm thành công", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(AddProductsActivity.this, AdProActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddProductsActivity.this, AdProActivity.class);
                startActivity(intent);
            }
        });
    }
}