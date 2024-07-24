package com.example.doan_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.doan_1.adapter.SanPhamAdapter;
import com.example.doan_1.model.SanPham;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class theodoiActivity extends AppCompatActivity implements SanPhamAdapter.ProductCallback {
    BottomNavigationView navigation;
    Toolbar toolbar3;
    RecyclerView recyclerChi;
    SanPhamAdapter sanPhamAdapter;
    ArrayList<SanPham> arrSanPham;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theodoi);
        navigation=  findViewById(R.id.navigation);
        toolbar3=findViewById(R.id.toolbar03);
        ActionToolBar();
        recyclerChi=findViewById(R.id.RecyclerChi);
        arrSanPham = new ArrayList<>();

        sanPhamAdapter = new SanPhamAdapter(this,arrSanPham,this);
        recyclerChi.setAdapter(sanPhamAdapter);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerChi.setLayoutManager(staggeredGridLayoutManager);
        retrieveProductsFromDatabase();



       /* //Nhận tên
        String ten = getIntent().getStringExtra("ten");
        tensp2.setText(ten + "");
        sp.setTen_sp(ten);
//Nhận giá
        int gia = getIntent().getIntExtra("gia", 0);
        DecimalFormat df = new DecimalFormat("#,### VNĐ");
        giasp2.setText(df.format(gia) + "");
        sp.setGia(getIntent().getIntExtra("gia", 0));
//chưa làm được mô tả
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

*/

        navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                if (id==R.id.navigation_home) {
                    Intent intent = new Intent(theodoiActivity.this, TrangchuActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                }
                else if (id==R.id.navigation_gift) {
                    Intent intent = new Intent(theodoiActivity.this, theodoiActivity.class);
                    startActivity(intent);
                    finish();
                    return true;


                }
                else if (id==R.id.navigation_history) {
                    Intent intent = new Intent(theodoiActivity.this, LichSuDHActivity.class);
                    startActivity(intent);
                }
                else if (id==R.id.navigation_setting) {
                    Intent intent = new Intent(theodoiActivity.this, accountActivity.class);
                    startActivity(intent);
                    finish();
                    return true;


                }return false;
            }
        });
    }

    private void retrieveProductsFromDatabase() {
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=firebaseDatabase.getReference("products");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrSanPham.clear();
                for (DataSnapshot snap : snapshot.getChildren()){
                    SanPham sanPham=snap.getValue(SanPham.class);
                    SanPham sp = new SanPham();
                    sp.setSurl(sanPham.getSurl());
                    sp.setTen_sp(sanPham.getTen_sp());
                    sp.setGia(sanPham.getGia());
                    sp.setMo_ta(sanPham.getMo_ta());
                    arrSanPham.add(sp);
                }
                sanPhamAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar3);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar3.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(theodoiActivity.this,TrangchuActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemClick(String ten, int price, String anh, String mota){
        Intent i = new Intent(this, ChitietActivity.class);
        i.putExtra("ten",ten);
        i.putExtra("gia",price);
        i.putExtra("anh",anh);
        i.putExtra("mota", mota);

        startActivity(i);
    }


}