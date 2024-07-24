package com.example.doan_1;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DangNhap extends AppCompatActivity {
    Button btnDNha;
    EditText edtUser, edtPass;
    TextView txtDni, tvquenmk;
    Intent intent;
    GoogleSignInClient mgg;
    Toolbar toolbar;
    private FirebaseAuth mAuthLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        btnDNha = findViewById(R.id.btnDNha);
        txtDni = findViewById(R.id.txtchuyenDK);
        edtUser = findViewById(R.id.edtUser);
        edtPass = findViewById(R.id.edtPass);
        toolbar = findViewById(R.id.Toolbar1);
        tvquenmk=findViewById(R.id.tvquenmk);
        SignInButton signInButton = findViewById(R.id.sign_in_button);


        tvquenmk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangNhap.this, Forgotpassword.class);
                startActivity(intent);
            }
        });
        mAuthLogin = FirebaseAuth.getInstance();

        ActionToolBar();
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInWithGoogle();
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mgg = GoogleSignIn.getClient(this, gso);
        FirebaseAuth userAuth = FirebaseAuth.getInstance();




        //Đăng nhập
        btnDNha.setOnClickListener(v -> {
            String email = edtUser.getText().toString();
            String password = edtPass.getText().toString();

            // Kiểm tra xem người dùng có phải là admin hay không
            if (email.equals("admin123@gmail.com") && password.equals("123456")) {
                // Nếu là admin, chuyển hướng đến trang admin
                Intent adminIntent = new Intent(DangNhap.this, AdminActivity.class);
                adminIntent.putExtra("login_admin", "Xin chào Admin!!!");
                startActivity(adminIntent);
            } else {
                // Nếu không phải là admin, tiến hành đăng nhập thông thường
                userAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                // Đăng nhập thành công, chuyển hướng đến trang chính
                                Log.d("Authentication", "signInWithEmail:success");
                                FirebaseUser user = userAuth.getCurrentUser();
                                Intent login = new Intent(DangNhap.this, TrangchuActivity.class);
                                login.putExtra("login_DN", "Đăng nhập thành công");
                                startActivity(login);
                            } else {
                                // Đăng nhập thất bại, hiển thị thông báo lỗi
                                Log.w("Authentication", "signInWithEmail:failure", task.getException());
                                Toast.makeText(DangNhap.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });

            }

        });
        txtDni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userAuth==null){
                    Intent intent = new Intent(DangNhap.this, MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(DangNhap.this, MainActivity.class);
                    startActivity(intent);
                }

            }
        });

    }


    private void signInWithGoogle() {
        Intent signInIntent = mgg.getSignInIntent();
        rsactivity_google.launch(signInIntent);
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
    ActivityResultLauncher<Intent> rsactivity_google = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK){
                try {
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    String email = account.getEmail();
                    String name = account.getDisplayName();
                    Toast.makeText(DangNhap.this, "Đăng nhập thành công "+"\n"+"email: "+ email+"\n"+"name: "+name, Toast.LENGTH_SHORT).show();
                    checkgg(email);
                } catch (Exception e){
                    Log.e(TAG,"onFailure: ",e);
                }
            }else {
                Toast.makeText(DangNhap.this, "Thất bại", Toast.LENGTH_SHORT).show();
            }
        }
    });

    public void checkgg(String email) {

            uploadToFirebase(email);
            startActivity(new Intent(DangNhap.this, MainActivity.class));

    }
    private void uploadToFirebase(String email) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, "defaultPassword")
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuthLogin.getCurrentUser();
                        } else {
                            Toast.makeText(DangNhap.this, "Firebase account creation failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
