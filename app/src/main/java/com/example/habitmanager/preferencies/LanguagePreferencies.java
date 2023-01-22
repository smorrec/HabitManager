package com.example.habitmanager.preferencies;

import android.content.Context;
import android.content.SharedPreferences;

public class LanguagePreferencies {
    private Context context;
    private static final String PREFS_NAME = "languagePref";
    private static final String KEY_LANGUAGE = "KEY_LANGUAGE";

    public LanguagePreferencies(Context context){
        this.context = context;
    }

    public void setLanguage(String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_LANGUAGE, value);
        editor.commit();
    }

    public String getLanguage(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_LANGUAGE, "es");
    }
}
