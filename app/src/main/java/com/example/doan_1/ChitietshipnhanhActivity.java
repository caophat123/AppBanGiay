package com.example.doan_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doan_1.model.Shipnhanh;

import java.util.ArrayList;
import java.util.List;

public class ChitietshipnhanhActivity extends AppCompatActivity {
    ImageView ivFeeShip;
    TextView txtMaFS;
    Intent intent=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietshipnhanh);
        addControls();

    }



    private void addControls() {
        ivFeeShip=findViewById(R.id.ivFreeShip);
        txtMaFS=findViewById(R.id.txtMaFS);
        intent=getIntent();
        Shipnhanh shipnhanh= (Shipnhanh) intent.getSerializableExtra("sn");
        ivFeeShip.setImageResource(shipnhanh.getHinhshipnhanh());
        txtMaFS.setText(shipnhanh.getMa());

        Spinner spinner = findViewById(R.id.spinner);
        List<String> quantities = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            quantities.add(String.valueOf(i)); // Thêm các số từ 1 đến 10 vào danh sách
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, quantities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedQuantity = parent.getItemAtPosition(position).toString();
                Toast.makeText(ChitietshipnhanhActivity.this, "Đã chọn: " + selectedQuantity, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}