package com.example.habitmanager.ui.habit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.habitmanager.R;
import com.example.habitmanager.adapter.CategoryAdapter;
import com.example.habitmanager.data.category.model.Category;
import com.example.habitmanager.data.habit.model.Habit;
import com.example.habitmanager.data.category.repository.CategoryRepository;
import com.example.habitmanager.databinding.FragmentAddEditHabitBinding;
import com.example.habitmanager.ui.utils.DatePickerFragment;
import com.example.habitmanager.ui.base.BaseFragment;
import com.google.android.material.textfield.TextInputEditText;

public class HabitManagerFragment extends BaseFragment {
    private FragmentAddEditHabitBinding binding;
    private HabitManagerViewModel viewModel;
    private ArrayAdapter<Category> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddEditHabitBinding.inflate(inflater);

        adapter = new CategoryAdapter(getContext(), R.layout.item_category, CategoryRepository.getInstance().getList());

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.CategorySpinner.setAdapter(adapter);

        Habit habit = new Habit();
        binding.setHabit(habit);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.txtStartDatePicker.setOnClickListener(startDatePicker -> showDatePickerDialog(binding.txtStartDatePicker));
        binding.txtEndDatePicker.setOnClickListener(endDatePicker -> showDatePickerDialog(binding.txtEndDatePicker));
        binding.fab.setOnClickListener(fab -> viewModel.addHabit(binding.getHabit(),
                (Category)binding.CategorySpinner.getSelectedItem()));
        binding.txtHabitName.addTextChangedListener(new HabitTextWatcher(binding.txtHabitName));
        binding.txtStartDatePicker.addTextChangedListener(new HabitTextWatcher(binding.txtStartDatePicker));

        if(getArguments() != null){
            showEdit();
        }
        initViewModel();
    }

    private void initViewModel(){
        viewModel = new ViewModelProvider(this).get(HabitManagerViewModel.class);
        viewModel.getResultMutableLiveData().observe(getViewLifecycleOwner(), habitManagerResult -> {
            switch (habitManagerResult){
                case NAMEEMPTY:
                    binding.txtHabitNameLayout.setError(getString(R.string.errorNameEmpty));
                    binding.txtHabitNameLayout.requestFocus();
                    break;
                case STARTDATEEMPTY:
                    binding.txtStartDateLayout.setError(getString(R.string.errorStartDateEmpty));
                    binding.txtStartDateLayout.requestFocus();
                    break;
                case FAILURE:
                    binding.txtHabitNameLayout.setError(getString(R.string.errorDuplicateHabit));
                    binding.txtHabitNameLayout.requestFocus();
                    break;
                case SUCCESS:
                    NavHostFragment.findNavController(this).navigateUp();
                    break;
            }
        });
    }

    public void showDatePickerDialog(TextInputEditText editText) {
        DatePickerFragment newFragment = DatePickerFragment.newInstance((datePicker, year, month, day) -> {
            final String selectedDate = day + " / " + (month+1) + " / " + year;
            editText.setText(selectedDate);
        });
        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }

    private void showEdit(){
        Habit habit = getArguments().getParcelable(Habit.KEY);
        binding.setHabit(habit.clone());

        binding.fab.setOnClickListener(view -> viewModel.editHabit(binding.getHabit(), habit,
                (Category) binding.CategorySpinner.getSelectedItem()));
    }

    class HabitTextWatcher implements TextWatcher {
        private TextView textView;

        private HabitTextWatcher(TextView textView){
            this.textView = textView;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            switch (textView.getId()){
                case R.id.txtHabitName:
                    binding.txtHabitNameLayout.setError(null);
                    break;
                case R.id.txtStartDatePicker:
                    binding.txtStartDateLayout.setError(null);
                    break;
            }
        }
    }
}