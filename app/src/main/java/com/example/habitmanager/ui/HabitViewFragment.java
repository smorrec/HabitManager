package com.example.habitmanager.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.habitmanager.R;
import com.example.habitmanager.data.model.Habit;
import com.example.habitmanager.databinding.FragmentHabitViewBinding;

public class HabitViewFragment extends Fragment {
    private FragmentHabitViewBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHabitViewBinding.inflate(inflater);
        binding.setHabit(getArguments().getParcelable(Habit.KEY));
        binding.fab.setOnClickListener(view -> goBack());
        return binding.getRoot();
    }

    private void goBack(){
        NavHostFragment.findNavController(this).navigate(R.id.action_habitViewFragment_to_habitListFragment);
    }
}