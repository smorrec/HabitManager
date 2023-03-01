package com.example.habitmanager.data.task.repository;

import android.util.Log;

import com.example.habitmanager.data.calendar.model.CalendarObject;
import com.example.habitmanager.data.calendar.repository.CalendarRepository;
import com.example.habitmanager.data.habit.model.Habit;
import com.example.habitmanager.data.habit.repository.HabitRepository;
import com.example.habitmanager.data.task.dao.HabitTaskDao;
import com.example.habitmanager.data.task.model.HabitTask;
import com.example.habitmanager.database.Database;
import com.example.habitmanager.ui.HabitManagerApplication;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

public class HabitTaskRepository {
    private HabitTaskDao habitTaskDao;
    private static HabitTaskRepository instance;

    private HabitTaskRepository(){
        habitTaskDao = Database.getINSTANCE().habitTaskDao();
    }

    public static HabitTaskRepository getInstance(){
        if(instance == null){
            instance = new HabitTaskRepository();
        }
        return instance;
    }

    public ArrayList<HabitTask> getList(){
        try {
            return HabitManagerApplication.getExecutor().submit(() ->
                    (ArrayList<HabitTask>) habitTaskDao.selectAll()).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void addTask(Habit habit) {
        ArrayList<CalendarObject> calendarObjects = CalendarRepository.getInstance().getList();
        for(CalendarObject calendarObject : calendarObjects){
               if (habit.hasTask(calendarObject)) {
                   HabitManagerApplication.getExecutor().submit(() ->
                           habitTaskDao.insert(new HabitTask(habit, calendarObject)));

               }
        }
    }

    public void deleteTask(Habit habit) {
        HabitManagerApplication.getExecutor().submit(() ->
                habitTaskDao.deleteWhereHabit(habit.getName()));
    }

    public void undo(Habit value) {
        addTask(value);
    }

    public void update(HabitTask task) {
        HabitManagerApplication.getExecutor().submit(() ->
                habitTaskDao.update(task));
    }
}
