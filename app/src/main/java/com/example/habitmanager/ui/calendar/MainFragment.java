package com.example.habitmanager.ui.calendar;

import android.content.res.Resources;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habitmanager.R;
import com.example.habitmanager.adapter.CalendarAdapter;
import com.example.habitmanager.adapter.HabitAdapter;
import com.example.habitmanager.adapter.TasksAdapter;
import com.example.habitmanager.data.calendar.model.CalendarObject;
import com.example.habitmanager.data.task.viewmodel.HabitTaskViewModel;
import com.example.habitmanager.data.task.viewmodel.State;
import com.example.habitmanager.databinding.FragmentMainBinding;
import com.example.habitmanager.ui.base.BaseFragment;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.time.LocalDate;
import java.util.Calendar;

public class MainFragment extends BaseFragment implements CalendarAdapter.OnItemClickListener{
    private FragmentMainBinding binding;
    private CalendarAdapter calendarAdapter;
    private TasksAdapter tasksAdapter;

    //private HabitTaskViewModel viewModel;
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
        //initViewModel();
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
                linearLayoutManager.scrollToPositionWithOffset(calendarAdapter.selectedPosition, (int) getResources().getDimension(R.dimen.offset));
                tasksAdapter.setSelectedCalendar(new CalendarObject(calendar));
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
        tasksAdapter = new TasksAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        binding.taskList.setLayoutManager(linearLayoutManager);
        binding.taskList.setAdapter(tasksAdapter);
    }

    /*
    private void initViewModel(){
        viewModel = new ViewModelProvider(this).get(HabitTaskViewModel.class);

        viewModel.getStateMutableLiveData().observe(getViewLifecycleOwner(), new Observer<State>() {
            @Override
            public void onChanged(State state) {
                switch (state){
                    case EMPTY:
                        //Mostrar lottie NOTASKS
                        break;
                    case FILL:


                }
            }
        });
    }
    */

    @Override
    public void onClick(View view, int position) {
        tasksAdapter.setSelectedCalendar(calendarAdapter.getItem(position));
        binding.taskList.startLayoutAnimation();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.VISIBLE);
    }

}