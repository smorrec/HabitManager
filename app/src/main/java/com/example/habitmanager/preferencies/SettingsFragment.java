package com.example.habitmanager.preferencies;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.example.habitmanager.R;

import java.util.Locale;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        initPreferenciesNotification();
        initPreferenciesTheme();
        initPreferenciesList();
        initPreferenciesLanguage();
    }
    //Falta implementar
    private void initPreferenciesLanguage() {
        Preference listPreference = getPreferenceManager().findPreference(getString(R.string.key_language));
        listPreference.setOnPreferenceChangeListener((preference, newValue) -> {
            (new LanguagePreferencies(getContext())).setLanguage((String) newValue);
            return true;
        });
    }

    private void initPreferenciesList() {
        Preference listPreference = getPreferenceManager().findPreference(getString(R.string.key_list));
        listPreference.setOnPreferenceChangeListener((preference, newValue) -> {
            (new ListPreferencies(getContext())).setOrder((String) newValue);
            return true;
        });
    }

    private void initPreferenciesTheme() {
        SwitchPreferenceCompat switchPreferenceCompat = getPreferenceManager().findPreference(getString(R.string.key_night));
        switchPreferenceCompat.setOnPreferenceChangeListener((preference, newValue) -> {
            (new ThemePreferencies(getContext())).setState((Boolean) newValue);

            if((Boolean) newValue){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            return true;
        });
    }

    private void initPreferenciesNotification() {
        SwitchPreferenceCompat switchPreferenceCompat = getPreferenceManager().findPreference(getString(R.string.key_activate_notification));
        switchPreferenceCompat.setOnPreferenceChangeListener((preference, newValue) -> {
            (new NotificationPreferencies(getContext())).setState((Boolean) newValue);
            return true;
        });
    }
}
