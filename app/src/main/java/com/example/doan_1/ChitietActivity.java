package com.example.doan_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.doan_1.adapter.GiohangAdapter;
import com.example.doan_1.adapter.SanPhamAdapter;
import com.example.doan_1.model.GioHang;
import com.example.doan_1.model.SanPham;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ChitietActivity extends AppCompatActivity  {
    TextView tensp2, giasp2, mota2, numberOdertxt;
    Button btnthem;
    ImageView imghinhanh, plussp, minussp;
    Spinner spinner;
    Toolbar toolbar;
    SanPhamAdapter sanPhamAdapter;

    ArrayList<SanPham> arr_Sanpham;
    SanPham sp=new SanPham();
    private DatabaseReference addFood = FirebaseDatabase.getInstance().getReference("GioHang");
    String ghID = addFood.push().getKey();
    Integer numberOder = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet);
        initView();
        ActionToolBar();
        tensp2 = findViewById(R.id.txttensp_chitiet);

        //initData();
//Nhận tên
        String ten = getIntent().getStringExtra("ten");
        tensp2.setText(ten + "");
        sp.setTen_sp(ten);

//Nhận giá
        int gia = getIntent().getIntExtra("gia", 0);
        DecimalFormat df = new DecimalFormat("#,### VNĐ");
        giasp2.setText(df.format(gia) + "");
        sp.setGia(getIntent().getIntExtra("gia", 0));
// được mô tả
        String mota = getIntent().getStringExtra("mota");
        mota2.setText(mota + "");
        sp.setMo_ta(mota);
//load ảnh
        String anh = getIntent().getStringExtra("anh");
        Glide.with(getApplicationContext())
                .load(anh)
                .placeholder(R.drawable.img_no_image) // Set a placeholder image (optional)
                .error(R.drawable.img_no_image)            // Set an error image (optional)
                .into(imghinhanh);

        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy thông tin từ các trường dữ liệu
                String ten = sp.getTen_sp();
                String gia = giasp2.getText().toString().trim();
                int soluong = Integer.parseInt(numberOder.toString().trim());
                String img = anh;
                // Tạo đối tượng GioHang
                GioHang gioHang = new GioHang(ten,gia,img,soluong,ghID,0);

                // Thực hiện thêm đối tượng GioHang vào Firebase Database
                addFood.child(ghID).setValue(gioHang)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Thêm thành công
                                Toast.makeText(ChitietActivity.this, "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();

                                // Tùy chỉnh phần xử lý sau khi thêm vào Firebase nếu cần

                                // Đặt lại các trường dữ liệu hoặc làm sạch UI nếu cần
                                tensp2.setText("");
                                giasp2.setText("");
                                mota2.setText("");
                                // numberOder = ;
                                //numberOdertxt.setText(String.valueOf(numberOder));
                                imghinhanh.setImageResource(R.drawable.logo);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Gặp lỗi khi thêm vào Firebase
                                Toast.makeText(ChitietActivity.this, "Lỗi khi thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                            }
                        });
                onBackPressed();
            }
        });
        plussp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberOder = numberOder + 1;
                numberOdertxt.setText(String.valueOf(numberOder));
            }
        });
        minussp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (numberOder>1){
                    numberOder=numberOder - 1;
                }
                numberOdertxt.setText(String.valueOf(numberOder));
            }
        });

    }





    private void initView() {
        giasp2=findViewById(R.id.txtgiasp_chitiet);
        mota2=findViewById(R.id.txtmota_chitiet);
        btnthem=findViewById(R.id.btnthemvaogiohang);
        spinner=findViewById(R.id.spinner);
        imghinhanh=findViewById(R.id.img_chitiet);
        toolbar=findViewById(R.id.Toolbar);
        plussp=findViewById(R.id.pluss);
        minussp=findViewById(R.id.minnus);
        numberOdertxt=findViewById(R.id.soluong);
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    protected void onResume() {

        super.onResume();
    }




}