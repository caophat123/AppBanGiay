package com.example.doan_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doan_1.adapter.ShipnhanhAdapter;
import com.example.doan_1.model.Shipnhanh;

public class ShipnhanhActivity extends AppCompatActivity {
    private ImageButton btnSieuSale;
    ListView lvship;
    ShipnhanhAdapter shipnhanhAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipnhanh);
        addControls();
        loadData();
        addEvents();
    }

    private void addEvents() {
        lvship.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Shipnhanh sn = shipnhanhAdapter.getItem(position);
                Intent intent = new Intent(ShipnhanhActivity.this, ChitietshipnhanhActivity.class);
                intent.putExtra("sn", sn);
                startActivity(intent);
            }
        });

        // Thêm sự kiện nhấn cho btnSieuSale
        btnSieuSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo Intent mới để chuyển đến Muasieusale_Activity
                Intent intent = new Intent(ShipnhanhActivity.this, Muasieusale_Activity.class);
                startActivity(intent);
            }
        });
    }

    private void loadData() {
        shipnhanhAdapter.add(new Shipnhanh(R.drawable.giaohang, "Hóa đơn tối thiểu trên 100k", "Mã FreeShip:78999$", 3, "Săn Deal Mã FreeShip"));
        shipnhanhAdapter.add(new Shipnhanh(R.drawable.giaohang, "Hóa đơn tối thiểu trên 100k", "Mã FreeShip:Y675@@:", 2, "Săn Deal Mã FreeShip"));
        shipnhanhAdapter.add(new Shipnhanh(R.drawable.giaohang, "Hóa đơn tối thiểu trên 100k", "Mã FreeShip:78999$", 3, "Săn Deal Mã FreeShip"));
        shipnhanhAdapter.add(new Shipnhanh(R.drawable.giaohang, "Hóa đơn tối thiểu trên 100k", "Mã FreeShip:Y675@@", 3, "Săn Deal Mã FreeShip"));
        shipnhanhAdapter.add(new Shipnhanh(R.drawable.giaohang, "Hóa đơn tối thiểu trên 100k", "Mã FreeShip:78999$", 3, "Săn Deal Mã FreeShip"));
        shipnhanhAdapter.add(new Shipnhanh(R.drawable.giaohang, "Hóa đơn tối thiểu trên 100k", "Mã FreeShip:12399$", 3, "Săn Deal Mã FreeShip"));
        shipnhanhAdapter.add(new Shipnhanh(R.drawable.giaohang, "Hóa đơn tối thiểu trên 100k", "Mã FreeShip:78999$", 3, "Săn Deal Mã FreeShip"));
        shipnhanhAdapter.add(new Shipnhanh(R.drawable.giaohang, "Hóa đơn tối thiểu trên 100k", "Mã FreeShip:78999$", 3, "Săn Deal Mã FreeShip"));
    }

    private void addControls() {
        lvship = findViewById(R.id.lvship);
        shipnhanhAdapter = new ShipnhanhAdapter(this, R.layout.items_shipnhanh);
        lvship.setAdapter(shipnhanhAdapter);

        // Khởi tạo btnSieuSale
        btnSieuSale = findViewById(R.id.btnSieuSale); // Đảm bảo rằng bạn đã đặt đúng id cho ImageButton trong layout của bạn
    }
}