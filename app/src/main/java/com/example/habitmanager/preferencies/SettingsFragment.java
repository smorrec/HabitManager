package com.example.habitmanager.preferencies;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.example.habitmanager.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        initPreferenciesNotification();
        initPreferenciesTheme();
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
