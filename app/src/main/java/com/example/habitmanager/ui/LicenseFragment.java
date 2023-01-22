package com.example.habitmanager.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.habitmanager.R;
import com.example.habitmanager.databinding.FragmentLicenseBinding;

public class LicenseFragment extends Fragment {
    private FragmentLicenseBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLicenseBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.licenseBtnMPA.setOnClickListener(this::showDescription);
        binding.licenseBtnAbout.setOnClickListener(this::showDescription);
    }

    private void hideDescription(View view){
        CardView content = null;
        ImageButton btn = (ImageButton) view;
        switch (view.getId()){
            case R.id.licenseBtnMPA:
                content = binding.contentMPA;
                break;
            case R.id.licenseBtnAbout:
                content = binding.contentAbout;
        }
        content.setVisibility(View.GONE);
        btn.setImageResource(R.drawable.ic_expand);
        btn.setOnClickListener(this::showDescription);
    }

    private void showDescription(View view){
        CardView content = null;
        ImageButton btn = (ImageButton) view;
        switch (view.getId()){
            case R.id.licenseBtnMPA:
                content = binding.contentMPA;
                break;
            case R.id.licenseBtnAbout:
                content = binding.contentAbout;
        }
        content.setVisibility(View.VISIBLE);
        btn.setImageResource(R.drawable.ic_hide);
        btn.setOnClickListener(this::hideDescription);
    }
}
