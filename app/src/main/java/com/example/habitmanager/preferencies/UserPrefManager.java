package com.example.habitmanager.preferencies;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Esta clase se encrga de leer o escribir cualquier información del usuario como preferencia del
 * sistema. Cualquier Fragment/Activity qeu necesite información del usuario utilizará una instancia
 * de esta clase.
 */
public class UserPrefManager {
    private Context context;
    private static final String PREFS_NAME = "userpref";
    private static final String KEY_EMAIL = "email";

    public UserPrefManager(Context context){
        this.context = context;
    }

    public void login(String email){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_EMAIL, email);
        editor.commit();
    }

    public boolean isUserLogged(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return !sharedPreferences.getString(KEY_EMAIL, "").isEmpty();
    }

    public void logOut(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_EMAIL);
        editor.commit();
    }

    public String getUserEmail(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMAIL, "");
    }
}
