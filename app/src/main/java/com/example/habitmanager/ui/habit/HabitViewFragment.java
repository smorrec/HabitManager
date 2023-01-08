package com.example.habitmanager.ui.habit;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.habitmanager.R;
import com.example.habitmanager.data.habit.model.Habit;
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
        binding.categoryImageView.setImageResource(binding.getHabit().getCategory().getPicture());
        binding.categoryTextView.setText(binding.getHabit().getCategory().getName());
        return binding.getRoot();
    }

    private void goBack(){
        NavHostFragment.findNavController(this).navigateUp();
    }
}