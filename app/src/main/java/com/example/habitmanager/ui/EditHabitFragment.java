package com.example.habitmanager.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.habitmanager.R;
import com.example.habitmanager.data.HabitRepository;
import com.example.habitmanager.data.model.Category;
import com.example.habitmanager.data.model.Habit;
import com.example.habitmanager.databinding.FragmentEditHabitBinding;


public class EditHabitFragment extends Fragment {
    private FragmentEditHabitBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEditHabitBinding.inflate(inflater);
        binding.setHabit(getArguments().getParcelable(Habit.KEY));
        binding.txtStartDatePicker.setOnClickListener(view -> showStartDatePickerDialog(view));
        binding.txtEndDatePicker.setOnClickListener(view -> showEndDatePickerDialog(view));
        binding.fab.setOnClickListener(view -> editHabit());
        ArrayAdapter<Category> adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, Category.values());
        binding.CategorySpinner.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void showStartDatePickerDialog(View v) {
        DatePickerFragment newFragment = DatePickerFragment.newInstance((datePicker, year, month, day) -> {
            final String selectedDate = day + " / " + (month+1) + " / " + year;
            binding.txtStartDatePicker.setText(selectedDate);
        });
        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }

    public void showEndDatePickerDialog(View v) {
        DatePickerFragment newFragment = DatePickerFragment.newInstance((datePicker, year, month, day) -> {
            final String selectedDate = day + " / " + (month+1) + " / " + year;
            binding.txtEndDatePicker.setText(selectedDate);
        });
        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }

    private void editHabit(){
        binding.getHabit().setCategory((Category)binding.CategorySpinner.getSelectedItem());
        NavHostFragment.findNavController(this).navigate(R.id.action_editHabitFragment_to_habitListFragment);
        Toast.makeText(getContext(), R.string.editedHabit, Toast.LENGTH_SHORT).show();
    }
}