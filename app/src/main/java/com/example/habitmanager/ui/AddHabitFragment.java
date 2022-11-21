package com.example.habitmanager.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.habitmanager.R;
import com.example.habitmanager.data.HabitRepository;
import com.example.habitmanager.data.model.Category;
import com.example.habitmanager.data.model.Habit;
import com.example.habitmanager.databinding.FragmentAddHabitBinding;

import java.util.Date;

public class AddHabitFragment extends Fragment {
    private HabitRepository repository;
    private FragmentAddHabitBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddHabitBinding.inflate(inflater);
        binding.txtStartDatePicker.setOnClickListener(view -> showStartDatePickerDialog(view));
        binding.txtEndDatePicker.setOnClickListener(view -> showEndDatePickerDialog(view));

        ArrayAdapter<Category> adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, Category.values());
        binding.CategorySpinner.setAdapter(adapter);
        binding.fab.setOnClickListener(view -> addHabit());
        binding.setHabit(new Habit());
        // Inflate the layout for this fragment
        return binding.getRoot();
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

    private void addHabit(){
        binding.getHabit().setCategory((Category)binding.CategorySpinner.getSelectedItem());
        repository = HabitRepository.getInstance();
        repository.getList().add(binding.getHabit());
        NavHostFragment.findNavController(this).navigate(R.id.action_addHabitFragment_to_habitListFragment);
        Toast.makeText(getContext(), R.string.addedHabit, Toast.LENGTH_SHORT).show();
    }
}