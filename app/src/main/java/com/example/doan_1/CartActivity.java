package com.example.doan_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.doan_1.adapter.GiohangAdapter;
import com.example.doan_1.adapter.SanPhamAdapter;
import com.example.doan_1.model.GioHang;
import com.example.doan_1.model.SanPham;
import com.example.doan_1.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    TextView txtTen_sp_gh, txtGia_sp_gh, txtsoluong_sp_gh, txtTongtien_sp_gh, icHuy;
    ImageView ivHinh_sp_gh;
    Toolbar toolbar2;
    RecyclerView recyclerGH;
    GiohangAdapter giohangAdapter; // Đổi tên biến theo quy ước
    SanPhamAdapter sanPhamAdapter;
    Button btnMuahang;
    ArrayList<GioHang> arr_GioHang;
    BottomNavigationView navigation;
    SanPham sp=new SanPham();
    GioHang gioHang=new GioHang();
    private DatabaseReference addUser = FirebaseDatabase.getInstance().getReference("User");

    private DatabaseReference GhOder = FirebaseDatabase.getInstance().getReference("GioHang");





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        navigation = findViewById(R.id.navigation);
        addControls();
        loadData();
        toolbar2 = findViewById(R.id.toolbar);
        ActiontoolBar();
        recyclerGH = findViewById(R.id.recyclerGiohang);

       // txtTongtien_sp_gh=findViewById(R.id.txtTongtien);
        recyclerGH.setLayoutManager(new LinearLayoutManager(this));
        arr_GioHang = new ArrayList<>();
        giohangAdapter = new GiohangAdapter(this , arr_GioHang);
        recyclerGH.setAdapter(giohangAdapter);
        retrieveProductsFromDatabase();
        String idGH = addUser.push().getKey();

       //updateTotalPrice();
        btnMuahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                View view = LayoutInflater.from(v.getRootView().getContext()).inflate(R.layout.dialog_xacnhandonhang, null);
                AlertDialog dialog = builder.create();
                EditText edtTenNguoiNhan, edtSoDienThoai, edtDiaChi;
                Button btnCancel, btnXacNhanDonHang;

                edtTenNguoiNhan = view.findViewById(R.id.edtTenNguoiNhan);
                edtSoDienThoai = view.findViewById(R.id.edtSoDienThoai);
                edtDiaChi = view.findViewById(R.id.edtDiaChi);
                btnCancel = view.findViewById(R.id.btnCancel);

                btnXacNhanDonHang = view.findViewById(R.id.btnXacNhanThanhToan);
                btnXacNhanDonHang.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String name = edtTenNguoiNhan.getText().toString().trim();
                        String sdt = edtSoDienThoai.getText().toString().trim();
                        String diachi = edtDiaChi.getText().toString().trim();

                        if (name.isEmpty() || sdt.isEmpty() || diachi.isEmpty()) {
                            Toast.makeText(CartActivity.this, "Vui lòng điền đầy đủ thông tin!!!", Toast.LENGTH_SHORT).show();
                        } else if (!TextUtils.isDigitsOnly(sdt)) {
                            Toast.makeText(CartActivity.this, "Số điện thoại sai định dạng!!!", Toast.LENGTH_SHORT).show();
                        } else {
                            User user = new User(idGH, name, sdt, diachi);
                            if (idGH != null) {
                                addUser.child(idGH).setValue(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                for (GioHang gioHangItem : arr_GioHang) {
                                                    DatabaseReference donHangRef = FirebaseDatabase.getInstance().getReference("DonHang").child(idGH).child("SanPham");
                                                    donHangRef.push().setValue(gioHangItem);
                                                }

                                                clearCart();

                                                Toast.makeText(CartActivity.this, "Đặt hàng thành công!!!", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();

                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(CartActivity.this, "Đặt hàng thất bại!!!", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                if (gioHang.getId() != null) {
                                    GhOder.child(gioHang.getId()).removeValue();
                                    arr_GioHang.clear();
                                }
                            }
                        }
                    }
                });



                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.setView(view);
                dialog.show();
            }
        });




        navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.navigation_home) {
                    Intent intent = new Intent(CartActivity.this, TrangchuActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (id == R.id.navigation_gift) {
                    Intent intent = new Intent(CartActivity.this, theodoiActivity.class);
                    startActivity(intent);
                    finish();
                    return true;


                } else if (id==R.id.navigation_history) {
                    Intent intent=new Intent(CartActivity.this, LichSuDHActivity.class);
                    startActivity(intent);
                    finish();
                    return true;

                } else if (id == R.id.navigation_setting) {
                    Intent intent = new Intent(CartActivity.this, accountActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                }
                return false;
            }
        });

    }


    private void loadData() {
       // txttongtien=findViewById(R.id.txtTongtien);
        btnMuahang=findViewById(R.id.btnMuahang);
        toolbar2 = findViewById(R.id.toolbar); // Thêm dòng này
        recyclerGH = findViewById(R.id.recyclerGiohang); // Thêm dòng này
        arr_GioHang = new ArrayList<>();
    }


    private void ActiontoolBar() {
        setSupportActionBar(toolbar2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar2.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }




     private void addControls() {
         recyclerGH=findViewById(R.id.recyclerGiohang);
         arr_GioHang=new ArrayList<>();
         giohangAdapter=new GiohangAdapter(this,arr_GioHang);
         recyclerGH.setAdapter(giohangAdapter);
         //GridLayoutManager gridLayoutManager=new GridLayoutManager(this,3);
         //recyclerViewNV.setLayoutManager(new LinearLayoutManager(this));
         StaggeredGridLayoutManager staggeredGridLayoutManager =
                 new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
         recyclerGH.setLayoutManager(staggeredGridLayoutManager);
     }

    private void retrieveProductsFromDatabase() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("GioHang");
        // String ID = databaseReference.push().getKey();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    arr_GioHang.clear();
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        GioHang sanPham = snap.getValue(GioHang.class);
                        sanPham.setUserId(userId);
                        String productId = snap.getKey();

                        // if (sanPham != null) { // Check if data is valid
                        GioHang sp = new GioHang();
                        sp.setId(productId); // Gán ID cho sản phẩm

                        sp.setHinh(sanPham.getHinh());
                        sp.setTen(sanPham.getTen());
                        sp.setGia(sanPham.getGia());
                        sp.setSoluong(sanPham.getSoluong());
                        //sp.setMo_ta(sanPham.getMo_ta());
                        giohangAdapter.add(sp);


                    }
                    giohangAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.w("TrangchuActivity", "Error retrieving products: ", error.toException());
                }
            });
        }
    }

    private void clearCart() {
        GhOder.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    snapshot.getRef().removeValue();
                }
                arr_GioHang.clear();
                giohangAdapter.notifyDataSetChanged(); // Cập nhật giao diện người dùng
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("ClearCart", "Failed to clear cart: " + databaseError.getMessage());
            }
        });
    }


}




