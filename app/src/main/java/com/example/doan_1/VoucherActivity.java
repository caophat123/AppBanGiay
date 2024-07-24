package com.example.doan_1;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.doan_1.adapter.VoucherAdapter;
import com.example.doan_1.model.Voucher;

public class VoucherActivity extends AppCompatActivity {
    Toolbar toolbar;
    ListView lvVoucher;
    VoucherAdapter voucherAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher);
        toolbar=findViewById(R.id.textView);
        addControls();
        loadData();
        ActionToolBar();
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadData() {
        voucherAdapter.add(new Voucher(R.drawable.ic_voucher,"Voucher giảm giá 25%",5));
        voucherAdapter.add(new Voucher(R.drawable.ic_voucher,"Voucher giảm giá 20%",10));
        voucherAdapter.add(new Voucher(R.drawable.ic_voucher,"Voucher giảm giá 10%",10));
        voucherAdapter.add(new Voucher(R.drawable.ic_voucher,"Voucher giảm giá 30%",2));
        voucherAdapter.add(new Voucher(R.drawable.ic_voucher,"Voucher giảm giá 30%",1));
        voucherAdapter.add(new Voucher(R.drawable.ic_voucher,"Voucher giảm giá 25%",5));
        voucherAdapter.add(new Voucher(R.drawable.ic_voucher,"Voucher giảm giá 20%",2));
        voucherAdapter.add(new Voucher(R.drawable.ic_voucher,"Voucher giảm giá 10%",2));
        voucherAdapter.add(new Voucher(R.drawable.ic_voucher,"Voucher giảm giá 15%",4));
        voucherAdapter.add(new Voucher(R.drawable.ic_voucher,"Voucher giảm giá 20%",3));
        voucherAdapter.add(new Voucher(R.drawable.ic_voucher,"Voucher giảm giá 10%",7));
        voucherAdapter.add(new Voucher(R.drawable.ic_voucher,"Voucher giảm giá 25%",1));
    }

    private void addControls() {
        lvVoucher=findViewById(R.id.lvVoucher);
        voucherAdapter=new VoucherAdapter(this,R.layout.items_vouchers);
        lvVoucher.setAdapter(voucherAdapter);
    }


}