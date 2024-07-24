package com.example.doan_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan_1.adapter.SanPhamAdapter;
import com.example.doan_1.adapter.SlideAdapter;
import com.example.doan_1.model.GioHang;
import com.example.doan_1.model.SanPham;
import com.example.doan_1.model.SlideIten;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TrangchuActivity extends AppCompatActivity implements SanPhamAdapter.ProductCallback {
    Toolbar toolbar;
    BottomNavigationView navigation;
    RecyclerView recyclerViewSP;
    SanPhamAdapter sanPhamAdapter;
    ArrayList<SanPham> arrSanPham;
    ImageButton btnShipnhanh;
    SearchView searchView;
    ViewPager2 viewPager2;
    DatabaseReference database;
    private Handler handler = new Handler();
    private Runnable runnable;
    private Timer timer;
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    DatabaseReference databaseReference=firebaseDatabase.getReference("products");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trangchu);


        String logintMessage = getIntent().getStringExtra("login_message");
        if (logintMessage != null) {
            // Hiển thị thông báo đăng xuất thành công
            Toast.makeText(this, logintMessage, Toast.LENGTH_LONG).show();
        }

        String login2Message = getIntent().getStringExtra("login_DN");
        if (login2Message != null) {
            // Hiển thị thông báo đăng xuất thành công
            Toast.makeText(this, login2Message, Toast.LENGTH_LONG).show();
        }

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("SHOP");
        searchView=findViewById(R.id.searchView);
//slide
        viewPager2 = findViewById(R.id.imageView6);
        List<SlideIten> slideIten = new ArrayList<>();
        slideIten.add(new SlideIten(R.drawable.convert));
        slideIten.add(new SlideIten(R.drawable.snk6));
        slideIten.add(new SlideIten(R.drawable.snkre));
        startAutoScroll(3000);
        viewPager2.setAdapter(new SlideAdapter(slideIten,viewPager2));

        navigation = findViewById(R.id.navigation);
        //txtSoLuongGioHang = findViewById(R.id.txtSoLuongGioHang);

        recyclerViewSP = findViewById(R.id.recyclerSanPham);
        arrSanPham = new ArrayList<>();

        sanPhamAdapter = new SanPhamAdapter(this,arrSanPham, (SanPhamAdapter.ProductCallback) this);
        recyclerViewSP.setAdapter(sanPhamAdapter);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerViewSP.setLayoutManager(staggeredGridLayoutManager);

        retrieveProductsFromDatabase();



        navigation.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.navigation_home) {
                    Intent intent = new Intent(TrangchuActivity.this, TrangchuActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (id == R.id.navigation_gift) {
                    Intent intent = new Intent(TrangchuActivity.this, theodoiActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (id==R.id.navigation_history) {
                    Intent intent=new Intent(TrangchuActivity.this, LichSuDHActivity.class);
                    startActivity(intent);

                } else if (id == R.id.navigation_setting) {
                    Intent intent = new Intent(TrangchuActivity.this, accountActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                }
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                timkiemSP(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                timkiemSP(s);
                return false;
            }
        });


        //return ;
    }

    private void timkiemSP(String searchText) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrSanPham.clear(); // Clear the previous list items

                if (searchText.isEmpty()) {
                    // If the search text is empty, display all products
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        SanPham model = dataSnapshot.getValue(SanPham.class);
                        arrSanPham.add(model);
                    }
                } else if (searchText==null) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        SanPham model = dataSnapshot.getValue(SanPham.class);
                        arrSanPham.add(model);
                    }
                } else {
                    // If there is search text, display matching products
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        SanPham model = dataSnapshot.getValue(SanPham.class);
                        if (model.getTen_sp().toLowerCase().contains(searchText.toLowerCase())) {
                            arrSanPham.add(model);
                        }
                    }
                }
                sanPhamAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled
            }
        });
    }

    private void retrieveProductsFromDatabase() {
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=firebaseDatabase.getReference("products");
       // String ID = databaseReference.push().getKey();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrSanPham.clear();
                for (DataSnapshot snap : snapshot.getChildren()){
                    SanPham sanPham=snap.getValue(SanPham.class);
                    String productId = snap.getKey();

                    // if (sanPham != null) { // Check if data is valid
                        SanPham sp = new SanPham();
                    sp.setID(productId); // Gán ID cho sản phẩm

                    sp.setSurl(sanPham.getSurl());
                        sp.setTen_sp(sanPham.getTen_sp());
                        sp.setGia(sanPham.getGia());
                        sp.setMo_ta(sanPham.getMo_ta());
                        sanPhamAdapter.add(sp);


                }
                sanPhamAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("TrangchuActivity", "Error retrieving products: ", error.toException());
            }
        });


    }

    public void openCartActivity(View view) {
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }


public void onItemClick(String ten, int price, String anh, String mota){
    Intent i = new Intent(this, ChitietActivity.class);
    i.putExtra("ten",ten);
    i.putExtra("gia",price);
    i.putExtra("anh",anh);
    i.putExtra("mota", mota);

    startActivity(i);
}

    public void openProfiletActivity(View view) {
        Intent intent = new Intent(this, accountActivity.class);
        startActivity(intent);
    }

    //

    private void startAutoScroll(int delay) {
        runnable = () -> {
            int currentItem = viewPager2.getCurrentItem();
            int totalItems = 3;

            if (currentItem < totalItems - 1) {
                viewPager2.setCurrentItem(currentItem + 1);
            } else {
                viewPager2.setCurrentItem(0);
            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        }, delay, delay);
    }

    public void onDestroy() {
        super.onDestroy();
        stopAutoScroll();
    }

    private void stopAutoScroll() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }


    public void opshopmall(View view) {
        Intent intent=new Intent(this,ShoppingActivity.class);
        startActivity(intent);
    }

    public void opVoucher(View view) {
        Intent intent=new Intent(this,VoucherActivity.class);
        startActivity(intent);
    }

    public void opship(View view) {
        Intent intent=new Intent(this,ShipnhanhActivity.class);
        startActivity(intent);
    }
}

