package com.example.doan_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    Button btnDki;
    EditText edtUser, edtPhone, txtMK, txtMK2,edtName;
    TextView linkTextView;
    Intent intent2;
    FirebaseAuth userAuth = FirebaseAuth.getInstance();
    FirebaseFirestore userData = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnDki = findViewById(R.id.btnDki);
        linkTextView = findViewById(R.id.txtDn);
        edtUser = findViewById(R.id.edtUser);
        edtPhone = findViewById(R.id.edtphone);
        txtMK = findViewById(R.id.txtMK);
        txtMK2 = findViewById(R.id.txtMK2);
        edtName=findViewById(R.id.edtName);

        String logoutMessage = getIntent().getStringExtra("logout_message");
        if (logoutMessage != null) {
            // Hiển thị thông báo đăng xuất thành công
            Toast.makeText(this, logoutMessage, Toast.LENGTH_LONG).show();
        }


        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            // User is signed in
            TrangChu();
        }

        //Đăng ký
        btnDki.setOnClickListener(v -> {
            String gmail, password, phone, nameuser;
            gmail = edtUser.getText().toString();
            password = txtMK.getText().toString();
            phone = edtPhone.getText().toString();
            nameuser=edtName.getText().toString();

            userAuth.createUserWithEmailAndPassword(gmail, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Authentication", "createUserWithEmail:success");
                            FirebaseUser user = userAuth.getCurrentUser();
                            String id = user.getUid();
                            storeUserData(gmail, phone,password,nameuser,id);
                            TrangChu();

                        }  else {
                            // If sign in fails, display a message to the user.
                            Log.w("Authentication", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        linkTextView.setOnClickListener(v -> LoginScreen());
    }

    //Lưu dữ liệu
    public void storeUserData(String gmail, String phone,String password,String nameuser, String id) {
        Map<String, Object> data = new HashMap<>();
        data.put("gmail", gmail);
        data.put("username",nameuser);
        data.put("phone", phone);
        data.put("birth",null);
        data.put("pass", password);

        userData.collection("users").document(id)
                .set(data)
                .addOnSuccessListener(aVoid -> Log.d("FireBase", "DocumentSnapshot successfully written!"))
                .addOnFailureListener(e -> Log.w("FireBase", "Error adding document", e));

    }

    public void TrangChu() {
        Intent login = new Intent(MainActivity.this, TrangchuActivity.class);
        login.putExtra("login_message", "Đăng nhập thành công");
        startActivity(login);
    }

    public void LoginScreen() {
        Intent login = new Intent(MainActivity.this, DangNhap.class);
        startActivity(login);
    }

}

