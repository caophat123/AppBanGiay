package com.example.doan_1.adapter;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import com.example.doan_1.R;

public class confignotification extends Application {

    public static final String CHANNEL_ID = "Sneaker Shop";

    @Override
    public void onCreate() {
        super.onCreate();
        config();
    }
    private void  config(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            // tên của notifi cần đăng ký
            CharSequence name =getString(R.string.channel_name);
            // mô tả
            String description = getString(R.string.channel_description);
            //độ ưu tiên của notifi
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            // sử dụng RingtoneManager để lấy uri của âm thanh
            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            //TẠO THÊM 1 AUDIO
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION).build();
            // Đăng ký Notifi
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            //set âm thanh cho notifi
            channel.setSound(uri, audioAttributes);
            // đăng ký channel
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
