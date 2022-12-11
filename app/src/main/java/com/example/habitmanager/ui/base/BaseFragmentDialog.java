package com.example.habitmanager.ui.base;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class BaseFragmentDialog extends DialogFragment {
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_BUNDLE = "result";
    public static final String KEY_REQUEST = "request";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if(getArguments() != null){
            String title = getArguments().getString(KEY_TITLE);
            String message = getArguments().getString(KEY_MESSAGE);
            String request = getArguments().getString(KEY_REQUEST);
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(title);
            builder.setMessage(message);
            builder.setPositiveButton(android.R.string.ok, (dialogInterface, i) -> {
                Bundle bundle = new Bundle();
                bundle.putBoolean(KEY_BUNDLE, true);
                getActivity().getSupportFragmentManager().setFragmentResult(request, bundle);
            });
            builder.setNegativeButton(android.R.string.cancel, (dialogInterface, i) -> {
                dismiss();
            });
            return builder.create();
        }
        return null;
    }
}
