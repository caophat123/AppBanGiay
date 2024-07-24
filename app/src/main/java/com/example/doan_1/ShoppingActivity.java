package com.example.doan_1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_1.adapter.ThongBaoAdapter;
import com.example.doan_1.model.Thongbao;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShoppingActivity extends AppCompatActivity {
    RecyclerView recyclerViewThongBao;
    private ThongBaoAdapter thongBaoAdapter;
    ArrayList<Thongbao> Glist;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("ThongBao");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);
        recyclerViewThongBao = findViewById(R.id.recyclerViewThongbao);
        recyclerViewThongBao.setHasFixedSize(true);

        recyclerViewThongBao.setLayoutManager(new GridLayoutManager(this, 1));
        Glist = new ArrayList<>();
        thongBaoAdapter = new ThongBaoAdapter(Glist, this);
        recyclerViewThongBao.setAdapter(thongBaoAdapter);

        root = FirebaseDatabase.getInstance().getReference("ThongBao");
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Glist.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Thongbao model = dataSnapshot.getValue(Thongbao.class);
                    Glist.add(model);
                }
                thongBaoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}