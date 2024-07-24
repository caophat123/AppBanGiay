package com.example.doan_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_1.adapter.ProductsAdapter;
import com.example.doan_1.model.Products;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class AdProActivity extends AppCompatActivity implements ProductsAdapter.ProductCallback1 {
    RecyclerView recyclerView;
    ProductsAdapter productsAdapter;
    FloatingActionButton btn_floating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_pro);

        btn_floating = findViewById(R.id.btn_floating);
        recyclerView = findViewById(R.id.rv_pro);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btn_floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdProActivity.this, AddProductsActivity.class);
                startActivity(intent);
            }
        });

        // Khởi tạo FirebaseRecyclerOptions và adapter ban đầu
        FirebaseRecyclerOptions<Products> options =
                new FirebaseRecyclerOptions.Builder<Products>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("products"), Products.class)
                        .build();
        productsAdapter = new ProductsAdapter(options, this,this);
        recyclerView.setAdapter(productsAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        // Bắt đầu lắng nghe sự thay đổi dữ liệu
        productsAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Dừng lắng nghe khi không cần thiết
        productsAdapter.stopListening();
    }

    @Override
    public void onbtnEditClick(String id, String ten_sp, int gia, int so_luong, String mo_ta, String surl) {
        // Xử lý sự kiện khi click nút chỉnh sửa sản phẩm
        Intent i = new Intent(this, UpdateSpActivity.class);
        i.putExtra("ten_sp", ten_sp);
        i.putExtra("gia", gia);
        i.putExtra("so_luong", so_luong);
        i.putExtra("surl", surl);
        i.putExtra("mo_ta", mo_ta);
        i.putExtra("id", id);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu; thêm items vào action bar nếu có
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                txtSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                txtSearch(newText);
                return false;
            }
        });
        return true;
    }

    private void txtSearch(String str) {
        // Tìm kiếm sản phẩm theo tên
        FirebaseRecyclerOptions<Products> options =
                new FirebaseRecyclerOptions.Builder<Products>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("products").orderByChild("ten_sp").startAt(str).endAt(str + "\uf8ff"), Products.class)
                        .build();
        // Cập nhật adapter với kết quả tìm kiếm mới
        productsAdapter.updateOptions(options);
    }
}
