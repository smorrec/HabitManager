package com.example.habitmanager.ui.habit;

import static com.example.habitmanager.ui.HabitManagerApplication.CHANNEL_ID;

import android.Manifest;
import android.app.PendingIntent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDeepLinkBuilder;
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
import com.example.habitmanager.preferencies.NotificationPreferencies;
import com.example.habitmanager.ui.base.BaseFragment;
import com.google.android.material.datepicker.MaterialDatePicker;
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
                (Category) binding.CategorySpinner.getSelectedItem()));
        binding.txtHabitName.addTextChangedListener(new HabitTextWatcher(binding.txtHabitName));
        binding.txtStartDatePicker.addTextChangedListener(new HabitTextWatcher(binding.txtStartDatePicker));

        if (getArguments() != null) {
            showEdit();
        }
        initViewModel();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(HabitManagerViewModel.class);
        viewModel.getResultMutableLiveData().observe(getViewLifecycleOwner(), habitManagerResult -> {
            switch (habitManagerResult) {
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
                    if((new NotificationPreferencies(getContext()).isActive())){
                        showAddNotification(binding.getHabit().getName());
                    }
                    break;
            }
        });
    }


    private void showAddNotification(String name) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Habit.KEY, binding.getHabit());

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            PendingIntent pendingIntent = new NavDeepLinkBuilder(getContext())
                    .setGraph(R.navigation.nav_graph)
                    .setDestination(R.id.habitListFragment)
                    .setArguments(bundle)
                    .createPendingIntent();

            NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), CHANNEL_ID)
                    .setSmallIcon(R.drawable.splashicon)
                    .setContentTitle(getString(R.string.addHabitNotTitle))
                    .setContentText(getString(R.string.addHabitNotTxt, name))
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getContext());
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
            notificationManagerCompat.notify(0, builder.build());

        }

    }

    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    showAddNotification(binding.getHabit().getName());
                } else {

                }
            });

    public void showDatePickerDialog(TextInputEditText editText) {
        MaterialDatePicker<Long> picker = MaterialDatePicker.Builder.datePicker().build();
        picker.addOnPositiveButtonClickListener(selection -> {
            editText.setText(picker.getHeaderText());
        });
        picker.show(getActivity().getSupportFragmentManager(), "datePicker");
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