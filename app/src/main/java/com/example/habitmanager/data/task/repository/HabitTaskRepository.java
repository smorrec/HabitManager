package com.example.habitmanager.data.task.repository;

import android.util.Log;

import com.example.habitmanager.data.calendar.model.CalendarObject;
import com.example.habitmanager.data.calendar.repository.CalendarRepository;
import com.example.habitmanager.data.habit.model.Habit;
import com.example.habitmanager.data.habit.repository.HabitRepository;
import com.example.habitmanager.data.task.model.HabitTask;

import java.util.ArrayList;
import java.util.Calendar;

public class HabitTaskRepository {
    private ArrayList<HabitTask> list;
    private static HabitTaskRepository instance;

    private HabitTaskRepository(){
        inicialize();
    }

    public static HabitTaskRepository getInstance(){
        if(instance == null){
            instance = new HabitTaskRepository();
        }
        return instance;
    }

    public ArrayList<HabitTask> getList(){
        return list;
    }

    private void inicialize(){
        list = new ArrayList<>();
        generateHabitTasks();
        Log.d("dashfb", String.valueOf(list.size()));
    }

    private void generateHabitTasks(){
        ArrayList<CalendarObject> calendarObjects = CalendarRepository.getInstance().getList();
        ArrayList<Habit> habits = HabitRepository.getInstance().getList();

        for(CalendarObject calendarObject : calendarObjects){
            for (Habit habit: habits){
                if (habit.hasTask(calendarObject)) {
                    list.add(new HabitTask(habit, calendarObject));
                }
            }
        }
        Log.d("dshfb", "generatedTasks");
    }

    public void addTask(Habit habit) {
        Calendar calendar = Calendar.getInstance();
        Log.d("Esta mierda funciona¿", String.valueOf(habit.getStartDate().compareTo(calendar)));
        ArrayList<CalendarObject> calendarObjects = CalendarRepository.getInstance().getList();
        for(CalendarObject calendarObject : calendarObjects){
               if (habit.hasTask(calendarObject)) {
                   Log.d("add task", habit.getName() + " " +  calendarObject.getCalendar() + " " + habit.getStartDate());
                   list.add(new HabitTask(habit, calendarObject));
               }
        }
    }

    public void deleteTask(Habit habit) {
        list.remove(new HabitTask(habit, new CalendarObject(Calendar.getInstance())));
    }
}
