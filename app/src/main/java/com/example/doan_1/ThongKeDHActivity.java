package com.example.doan_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.Toolbar;

import com.example.doan_1.adapter.LichsuAdapter;
import com.example.doan_1.model.GioHang;
import com.example.doan_1.model.Lichsu;
import com.example.doan_1.model.SanPham;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ThongKeDHActivity extends AppCompatActivity {
    ArrayList<Lichsu> arrlichsu;
    LichsuAdapter lichsuAdapter;
    RecyclerView recyclerViewDH;
    BottomNavigationView navigation;
    Toolbar toolbarDH;
    private DatabaseReference addUser = FirebaseDatabase.getInstance().getReference("User");

    String idGH = addUser.push().getKey();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke_dhactivity);

        recyclerViewDH = findViewById(R.id.rv_DH);

        arrlichsu = new ArrayList<>();
        lichsuAdapter=new LichsuAdapter(this,arrlichsu);

        recyclerViewDH.setAdapter(lichsuAdapter);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerViewDH.setLayoutManager(staggeredGridLayoutManager);

        retrieveProductsFromDatabase();
        ActiontoolBar();

    }

    private void retrieveProductsFromDatabase() {
        DatabaseReference donHangRef = FirebaseDatabase.getInstance().getReference("DonHang");
        donHangRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrlichsu.clear();
                for (DataSnapshot orderSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot sanPhamSnapshot : orderSnapshot.child("SanPham").getChildren()) {
                        String tenLS2 = sanPhamSnapshot.child("ten").getValue(String.class);
                        String hinhLS2 = sanPhamSnapshot.child("hinh").getValue(String.class);
                        String giaLS2 = sanPhamSnapshot.child("gia").getValue(String.class);
                        String maLS2=sanPhamSnapshot.child("id").getValue(String.class);
                        String soluongLS2= String.valueOf(sanPhamSnapshot.child("soluong").getValue(int.class));

                        Lichsu ls = new Lichsu(tenLS2, hinhLS2, giaLS2,maLS2,soluongLS2);
                        arrlichsu.add(ls);
                    }
                }
                lichsuAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("RetrieveProducts", "Failed to retrieve products: " + error.getMessage());
            }
        });
    }



    private void ActiontoolBar() {
        toolbarDH = findViewById(R.id.toolbarDH);
        setSupportActionBar(toolbarDH);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarDH.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
