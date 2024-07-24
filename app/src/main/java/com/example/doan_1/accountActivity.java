package com.example.doan_1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan_1.model.Username;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class accountActivity extends AppCompatActivity {
    BottomNavigationView navigation;
    View btnToiLogOut;
    TextView txttentk, txttenmail, txtsoDienThoai, txttenUser;
    FirebaseAuth userAuth = FirebaseAuth.getInstance();
    FirebaseFirestore userData = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        btnToiLogOut = findViewById(R.id.btnToiLogOut);
        navigation = findViewById(R.id.navigation);


        txttenmail = findViewById(R.id.txtTenmail);
        txtsoDienThoai = findViewById(R.id.txtSoDienThoai);
        txttenUser = findViewById(R.id.txtTenUser);

        String id = userAuth.getCurrentUser().getUid();
        DocumentReference documentReference = userData.collection("users").document(id);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
               if (documentSnapshot!=null) {
                   txttenmail.setText(documentSnapshot.getString("gmail"));
                   txttenUser.setText(documentSnapshot.getString("username"));
                   txtsoDienThoai.setText(documentSnapshot.getString("phone"));
               }
            }
        });






        navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                if (id==R.id.navigation_home) {
                    Intent intent = new Intent(accountActivity.this, TrangchuActivity.class);
                    startActivity(intent);
                    finish();
                    return true;}
                    else if (id==R.id.navigation_gift) {
                        Intent intent = new Intent(accountActivity.this, theodoiActivity.class);
                        startActivity(intent);
                        finish();
                        return true;


                }
                else if (id==R.id.navigation_history) {
                    Intent intent = new Intent(accountActivity.this, LichSuDHActivity.class);
                    startActivity(intent);
                }
                else if (id==R.id.navigation_setting) {
                    Intent intent = new Intent(accountActivity.this, accountActivity.class);
                    startActivity(intent);
                    finish();
                    return true;


                }return false;
            }
        });
        btnToiLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();

            }
        });





    }
    private void signOut() {
        // Đăng xuất Firebase
        userAuth.signOut();
        userAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // Nếu người dùng không còn đăng nhập, chuyển đến MainActivity
                    Intent i = new Intent(accountActivity.this, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("logout_message", "Đăng xuất thành công");
                    startActivity(i);
                    finish(); // Đóng Activity hiện tại
                }
            }
        });
    }
}