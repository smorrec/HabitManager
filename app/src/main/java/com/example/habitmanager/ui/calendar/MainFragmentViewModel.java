package com.example.habitmanager.ui.calendar;
import androidx.lifecycle.ViewModel;

import com.example.habitmanager.data.calendar.model.CalendarObject;
import com.example.habitmanager.data.calendar.repository.CalendarRepository;
import com.example.habitmanager.data.habit.model.Habit;
import com.example.habitmanager.data.habit.repository.HabitRepository;
import com.example.habitmanager.data.task.model.HabitTask;
import com.example.habitmanager.viewmodel.StateLiveDataList;

import java.util.ArrayList;

public class MainFragmentViewModel extends ViewModel {
    private StateLiveDataList<ArrayList<CalendarObject>> stateLiveDataList = new StateLiveDataList<>();

    public void getDataList(){
        stateLiveDataList.setLoading();

        ArrayList<CalendarObject> list = CalendarRepository.getInstance().getList();

        if(list.isEmpty()){
            stateLiveDataList.setNoData();
        }else {
            generateHabitTasks(list);
            stateLiveDataList.setSuccess(list);
        }
    }

    private void generateHabitTasks(ArrayList<CalendarObject> list){
        ArrayList<Habit> habits = HabitRepository.getInstance().getList();

        for(CalendarObject calendarObject : list){
            for (Habit habit: habits){
                if (habit.hasTask(calendarObject)) {
                    calendarObject.addHabitTask(new HabitTask(habit, calendarObject));
                }
            }
        }
    }

    public StateLiveDataList<ArrayList<CalendarObject>> getStateLiveDataList() {
        return stateLiveDataList;
    }
}
