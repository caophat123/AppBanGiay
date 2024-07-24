package com.example.doan_1;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class AdminActivity extends AppCompatActivity {
    Button AdProducts, AdDH,AdTB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        String loginadMessage = getIntent().getStringExtra("login_admin");
        if (loginadMessage != null) {
            // Hiển thị thông báo đăng xuất thành công
            Toast.makeText(this, loginadMessage, Toast.LENGTH_LONG).show();
        }

        AdProducts=findViewById(R.id.AdProducts);
        AdDH=findViewById(R.id.AdDH);
        AdTB=findViewById(R.id.AdTB);
        AdProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, AdProActivity.class);
                startActivity(intent);
            }
        });
        AdDH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, ThongKeDHActivity.class);
                startActivity(intent);
            }
        });
        AdTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminActivity.this, AddThongBao.class);
                startActivity(intent);
            }
        });
    }
}