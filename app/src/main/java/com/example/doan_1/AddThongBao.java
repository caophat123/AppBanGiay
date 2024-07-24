package com.example.doan_1;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.doan_1.adapter.confignotification;
import com.example.doan_1.model.Thongbao;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

public class AddThongBao extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private Button btnUploadThongBao, btnBackThongBao;

    private EditText edtTenThongBao, edtNoiDungThongbao;

    private TextView imghinh;

    private TextView tvDateThongBaoadd;

    //    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("ThongBao");
    private DatabaseReference thongBaoReference = FirebaseDatabase.getInstance().getReference("ThongBao");
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_thong_bao);

        btnUploadThongBao = findViewById(R.id.btnUploadThongBao);
        btnBackThongBao = findViewById(R.id.btnBackThongBao);
        edtTenThongBao = findViewById(R.id.edtTenThongBao);
        edtNoiDungThongbao = findViewById(R.id.edtNoiDungThongBao);
        tvDateThongBaoadd = findViewById(R.id.tvDateThongBAOadd);
        imghinh = findViewById(R.id.imghinh);

        btnBackThongBao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnUploadThongBao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadToFirebase();
                Bitmap logo = BitmapFactory.decodeResource(getResources(), R.mipmap.logo);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(AddThongBao.this, confignotification.CHANNEL_ID)
                        //icon sẽ hiện trên soud
                        .setSmallIcon(R.mipmap.logo)
                        //tiêu đề của notifi
                        .setContentTitle("Bạn có 1 thông báo mới ")
                        //nội dung của notifi
//                        .setContentText("đây là nội dung bạn nên xem 'bấm vào'")
                        //truyền đạt hình ảnh vào noti
                        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(logo).bigLargeIcon(null))
                        //hiện thị icon bên phải khi noti ở dạng thu gọn
                        .setLargeIcon(logo).setColor(Color.GREEN).setAutoCancel(true);
                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(AddThongBao.this);
                // code kiểm tra quyền notifi
                if (ActivityCompat.checkSelfPermission(AddThongBao.this, Manifest.permission.POST_NOTIFICATIONS) ==
                        PackageManager.PERMISSION_GRANTED) {
                    // nếu đa có quyền ta thực hiện push noti
                    notificationManagerCompat.notify((int) new Date().getTime(), builder.build());
                } else {
                    //nếu k có quyền tthì sẽ xin
                    ActivityCompat.requestPermissions(AddThongBao.this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 7979);
                }

            }
        });

        tvDateThongBaoadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDatePickerDialog();
            }
        });
    }

    private void ShowDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddThongBao.this, this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String curren = dayOfMonth + "/" + month + "/" + year;
        tvDateThongBaoadd.setText(curren);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    private void uploadToFirebase() {
        String ten = edtTenThongBao.getText().toString().trim();
        String noidung = edtNoiDungThongbao.getText().toString().trim();
        String hinh = imghinh.getText().toString().trim();
        String date = tvDateThongBaoadd.getText().toString().trim();

        if (ten.isEmpty() || noidung.isEmpty() || date.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        } else {
            Thongbao thongbao = new Thongbao(ten, noidung, date);
            String thongBaoId = thongBaoReference.push().getKey();

            if (thongBaoId != null) {
                thongBaoReference.child(thongBaoId)
                        .setValue(thongbao)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(AddThongBao.this, "Đăng thông báo thành công", Toast.LENGTH_SHORT).show();
                                   // onBackPressed();
                                } else {
                                    Toast.makeText(AddThongBao.this, "Đăng thông báo thất bại", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 7979){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                    sendNotification();
                }
            }
        }
    }

    private void sendNotification() {

    }

    private void sendFCMNotificationToAllUsers(String title, String message) {

    }
}