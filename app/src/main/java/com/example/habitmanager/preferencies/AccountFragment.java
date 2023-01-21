package com.example.habitmanager.preferencies;

import static android.text.InputType.TYPE_CLASS_TEXT;
import static android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import com.example.habitmanager.R;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class AccountFragment extends PreferenceFragmentCompat {
    private static final String FILE_NAME = "encriptation_shared_prefs";
    private static final String KEY_PASSWORD = "key_password";
    private SharedPreferences sharedPreferences;
    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.account_preferences, rootKey);
        initPreferenciasEmail();
        try {
            initEcnriptacion();
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
        initPreferenciasPassword();
    }

    private void initPreferenciasPassword() {
        EditTextPreference edPassword = getPreferenceManager().findPreference(getString(R.string.key_password));

        edPassword.setOnBindEditTextListener(editText -> {
            edPassword.setText(sharedPreferences.getString(KEY_PASSWORD, ""));
            editText.setInputType(TYPE_CLASS_TEXT | TYPE_TEXT_VARIATION_PASSWORD);
            editText.selectAll();
        });

        edPassword.setOnPreferenceChangeListener((preference, newValue) -> {
            sharedPreferences.edit().putString(KEY_PASSWORD, (String) newValue).commit();
            return true;
        });
    }

    private void initEcnriptacion() throws GeneralSecurityException, IOException {
        MasterKey mainKey = new MasterKey.Builder(getContext())
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build();

        sharedPreferences = EncryptedSharedPreferences
                .create(
                        getContext(),
                        FILE_NAME,
                        mainKey,
                        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                );
    }

    /**
     * Método que inicializa el tipo de dato admitido editText, que no esté habilitado
     */
    private void initPreferenciasEmail() {
        EditTextPreference edEmail = getPreferenceManager().findPreference(getString(R.string.key_email));
        edEmail.setOnBindEditTextListener(editText -> edEmail.setText(new UserPrefManager(getContext()).getUserEmail()));
        edEmail.setOnPreferenceChangeListener((preference, newValue) -> {
            new UserPrefManager(getContext()).changeEmail((String) newValue);
            return true;
        });
    }
}
