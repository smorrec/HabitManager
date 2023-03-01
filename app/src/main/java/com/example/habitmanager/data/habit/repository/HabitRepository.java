package com.example.habitmanager.data.habit.repository;

import com.example.habitmanager.data.category.model.Category;
import com.example.habitmanager.data.habit.dao.HabitDao;
import com.example.habitmanager.data.habit.model.Habit;
import com.example.habitmanager.database.Database;
import com.example.habitmanager.ui.HabitManagerApplication;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class HabitRepository {
    private HabitDao habitDao;
    private static HabitRepository instance;

    private HabitRepository(){
        habitDao = Database.getINSTANCE().habitDao();
    }

    public static HabitRepository getInstance(){
        if(instance == null){
            instance = new HabitRepository();
        }
        return instance;
    }

    public ArrayList<Habit> getList(){
        try {
            return HabitManagerApplication.getExecutor().submit(() ->
                    (ArrayList<Habit>) habitDao.selectAll()).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean addHabit(Habit habit, int category) {
        try {
            habit.setCategoryId(category);
            return HabitManagerApplication.getExecutor().submit(() ->
                    habitDao.insert(habit)).get() > 0;
        } catch (ExecutionException | InterruptedException e) {
            return false;
        }
    }

    public boolean editHabit(Habit habit, int category) {

        try {
            habit.setCategoryId(category);
            return HabitManagerApplication.getExecutor().submit(() ->
                    habitDao.update(habit)).get() > 0;
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteHabit(Habit habit){
        HabitManagerApplication.getExecutor().submit(() ->
                habitDao.delete(habit));
    }

    public void undo(Habit habit) {
        HabitManagerApplication.getExecutor().submit(() ->
                habitDao.insert(habit));
    }

    public Habit selectByName(String name){
        try {
            return HabitManagerApplication.getExecutor().submit(() -> habitDao.selectByName(name)).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
