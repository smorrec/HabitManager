package com.example.habitmanager.ui;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.appcompat.app.AppCompatDelegate;

import com.example.habitmanager.R;
import com.example.habitmanager.preferencies.ThemePreferencies;

public class HabitManagerApplication extends Application {
    public static final String CHANNEL_ID = "id";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        initTheme();
    }

    /**
     * En este método se debe comprobar que la API es mayor a 26 porque la clase NotificationChannel
     * no se encuentra en la libreria de soporte
     */
    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "CrearHabitoCH";
            String description = "Ha creado un hábito";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
    }

    private void initTheme(){
        ThemePreferencies themePreferencies = new ThemePreferencies(this);
        if(themePreferencies.isNightActive()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}
