package com.example.habitmanager.preferencies;

import android.content.Context;
import android.content.SharedPreferences;

public class NotificationPreferencies {
    private Context context;
    private static final String PREFS_NAME = "notificationPref";
    private static final String KEY_NOTIFICATION = "KEY_MOTIFICATION";

    public NotificationPreferencies(Context context){
        this.context = context;
    }

    public void setState(Boolean value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_NOTIFICATION, value);
        editor.commit();
    }

    public boolean isActive(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(KEY_NOTIFICATION, true);
    }
}
