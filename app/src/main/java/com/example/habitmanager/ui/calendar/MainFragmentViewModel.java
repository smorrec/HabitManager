package com.example.habitmanager.ui.calendar;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.habitmanager.data.calendar.model.CalendarObject;
import com.example.habitmanager.data.calendar.repository.CalendarRepository;
import com.example.habitmanager.data.habit.model.Habit;
import com.example.habitmanager.data.habit.repository.HabitRepository;
import com.example.habitmanager.data.task.model.HabitTask;
import com.example.habitmanager.viewmodel.StateLiveDataList;

import java.util.ArrayList;

public class MainFragmentViewModel extends ViewModel {
    private StateLiveDataList<ArrayList<CalendarObject>> stateLiveDataListCalendar = new StateLiveDataList<>();
    private StateLiveDataList<ArrayList<Habit>> stateLiveDataListHabit = new StateLiveDataList<>();

    public void getDataList(){
        ArrayList<Habit> habitList = HabitRepository.getInstance().getList();
        if (habitList == null){
            stateLiveDataListHabit.setNoData();
        }else {
            stateLiveDataListHabit.setSuccess(habitList);
        }

        ArrayList<CalendarObject> calendarList = CalendarRepository.getInstance().getList();
        generateHabitTasks(calendarList);
        stateLiveDataListCalendar.setSuccess(calendarList);

    }

    private void generateHabitTasks(ArrayList<CalendarObject> list){
        for(CalendarObject calendarObject : list){
            for (Habit habit: stateLiveDataListHabit.getValue().getData()){
                if (habit.hasTask(calendarObject)) {
                    calendarObject.addHabitTask(new HabitTask(habit, calendarObject));
                }
            }
        }
    }

    public StateLiveDataList<ArrayList<CalendarObject>> getStateLiveDataList() {
        return stateLiveDataListCalendar;
    }

    public StateLiveDataList<ArrayList<Habit>> getMutableLiveData() {
        return stateLiveDataListHabit;
    }
}
