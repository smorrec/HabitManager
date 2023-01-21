package com.example.habitmanager.preferencies;

import android.content.Context;
import android.content.SharedPreferences;

public class ThemePreferencies {
    private Context context;
    private static final String PREFS_NAME = "themePref";
    private static final String KEY_THEME = "KEY_THEME";

    public ThemePreferencies(Context context){
        this.context = context;
    }

    public void setState(Boolean value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_THEME, value);
        editor.commit();
    }

    public boolean isNightActive(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(KEY_THEME, true);
    }
}
