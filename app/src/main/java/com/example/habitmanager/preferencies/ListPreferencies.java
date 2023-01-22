package com.example.habitmanager.preferencies;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import com.example.habitmanager.R;

public class ListPreferencies {
    private Context context;
    private static final String PREFS_NAME = "listPref";
    private static final String KEY_LIST = "KEY_LIST";

    public ListPreferencies(Context context){
        this.context = context;
    }

    public void setOrder(String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_LIST, value);
        editor.commit();
    }

    public String getOrder(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_LIST, "");
    }
}
