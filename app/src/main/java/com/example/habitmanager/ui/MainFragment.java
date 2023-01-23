package com.example.habitmanager.ui;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habitmanager.R;
import com.example.habitmanager.adapter.CalendarAdapter;
import com.example.habitmanager.adapter.HabitAdapter;
import com.example.habitmanager.databinding.FragmentMainBinding;
import com.example.habitmanager.ui.base.BaseFragment;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.time.LocalDate;
import java.util.Calendar;

public class MainFragment extends BaseFragment implements CalendarAdapter.OnItemClickListener, HabitAdapter.OnItemClickListener{
    private FragmentMainBinding binding;
    private CalendarAdapter calendarAdapter;
    private HabitAdapter habitAdapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater);
        initRvCalendar();
        initRvTasks();
        getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.VISIBLE);
        return binding.getRoot();

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_calendar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_selectDay:
                selectCalendar();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void selectCalendar() {
        MaterialDatePicker<Long> picker = MaterialDatePicker.Builder.datePicker().build();
        picker.addOnPositiveButtonClickListener(selection -> {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(selection);
            if(calendarAdapter.selectDay(calendar)) {
                linearLayoutManager.scrollToPositionWithOffset(calendarAdapter.selectedPosition, binding.MainFragment.getWidth()/2);
            }
        });
        picker.show(getActivity().getSupportFragmentManager(), "datePicker");

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initRvCalendar(){
        calendarAdapter = new CalendarAdapter(this);

        linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);

        binding.calendarList.setLayoutManager(linearLayoutManager);

        binding.calendarList.setAdapter(calendarAdapter);
    }

    private void initRvTasks(){
        habitAdapter = new HabitAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        binding.taskList.setLayoutManager(linearLayoutManager);
        binding.taskList.setAdapter(habitAdapter);
    }

    @Override
    public void onClick(View view, int position) {

    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}