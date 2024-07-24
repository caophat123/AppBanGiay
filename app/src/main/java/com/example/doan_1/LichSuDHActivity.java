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

public class LichSuDHActivity extends AppCompatActivity {
    ArrayList<Lichsu> arrlichsu;
    LichsuAdapter lichsuAdapter;
    RecyclerView recyclerViewLS;
    BottomNavigationView navigation;
    Toolbar toolbarLs;
    private DatabaseReference addUser = FirebaseDatabase.getInstance().getReference("User");

    String idGH = addUser.push().getKey();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_su_dhactivity);

        recyclerViewLS = findViewById(R.id.recyclerLichsu);
        navigation = findViewById(R.id.navigationLS);

        arrlichsu = new ArrayList<>();
        lichsuAdapter=new LichsuAdapter(this,arrlichsu);

        recyclerViewLS.setAdapter(lichsuAdapter);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerViewLS.setLayoutManager(staggeredGridLayoutManager);

        retrieveProductsFromDatabase();
        ActiontoolBar();

        navigation.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.navigation_home) {
                    Intent intent = new Intent(LichSuDHActivity.this, TrangchuActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (id == R.id.navigation_gift) {
                    Intent intent = new Intent(LichSuDHActivity.this, theodoiActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (id == R.id.navigation_history) {
                    // Already on the history page
                    return true;
                } else if (id == R.id.navigation_setting) {
                    Intent intent = new Intent(LichSuDHActivity.this, accountActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                }
                return false;
            }
        });
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
        toolbarLs=findViewById(R.id.toolbarLS);
        setSupportActionBar(toolbarLs);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarLs.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
