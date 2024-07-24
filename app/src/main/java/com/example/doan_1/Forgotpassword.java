package com.example.doan_1;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgotpassword extends AppCompatActivity {

    private EditText edtEmailQuen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);


        edtEmailQuen = findViewById(R.id.edtEmailQuen);
        findViewById(R.id.btnRecoverPassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtEmailQuen.getText().toString().trim();
                if (!TextUtils.isEmpty(email)) {
                    sendRecoveryEmail(email);
                    Toast.makeText(Forgotpassword.this, "vui lòng kiểm tra Email", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Forgotpassword.this, "Vui lòng nhập Email đã đăng ký ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendRecoveryEmail(String email) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(Forgotpassword.this, DangNhap.class);
                            intent.putExtra("email", email);
                            startActivity(intent);
                        } else {
                            Log.w(TAG, "Lỗi khi gửi email đặt lại mật khẩu", task.getException());
                            Toast.makeText(Forgotpassword.this, "Lỗi khi gửi email đặt lại mật khẩu", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}