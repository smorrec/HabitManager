package com.example.habitmanager.data.task.repository;

import com.example.habitmanager.data.calendar.model.CalendarObject;
import com.example.habitmanager.data.calendar.repository.CalendarRepository;
import com.example.habitmanager.data.habit.model.Habit;
import com.example.habitmanager.data.habit.repository.HabitRepository;
import com.example.habitmanager.data.task.model.HabitTask;

import java.util.ArrayList;

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

    }
}
