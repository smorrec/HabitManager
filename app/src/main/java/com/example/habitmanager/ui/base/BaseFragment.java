package com.example.habitmanager.ui.base;

import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {
    public void showError(String error){
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }
    public void showError(int error){
        Toast.makeText(getContext(), getResources().getText(error), Toast.LENGTH_SHORT).show();
    }
}
