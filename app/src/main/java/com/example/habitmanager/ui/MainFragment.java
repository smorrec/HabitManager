package com.example.habitmanager.ui;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habitmanager.R;
import com.example.habitmanager.adapter.CalendarAdapter;
import com.example.habitmanager.adapter.HabitAdapter;
import com.example.habitmanager.databinding.FragmentMainBinding;

import java.time.LocalDate;

public class MainFragment extends Fragment implements CalendarAdapter.OnItemClickListener, HabitAdapter.OnItemClickListener{
    private FragmentMainBinding binding;
    private CalendarAdapter calendarAdapter;
    private HabitAdapter habitAdapter;
    private LocalDate date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            date = LocalDate.now();
        }
        initRvCalendar();
        initRvTasks();
        getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.VISIBLE);
        return binding.getRoot();

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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);

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