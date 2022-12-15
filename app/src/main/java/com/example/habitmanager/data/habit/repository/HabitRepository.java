package com.example.habitmanager.data.habit.repository;

import com.example.habitmanager.data.category.model.Category;
import com.example.habitmanager.data.category.repository.CategoryRepository;
import com.example.habitmanager.data.habit.model.Habit;

import java.util.ArrayList;

public class HabitRepository {
    private ArrayList<Habit> list;
    private static HabitRepository instance;
    private int deletedPosition = -1;

    private HabitRepository(){
        initialize();
    }

    public static HabitRepository getInstance(){
        if(instance == null){
            instance = new HabitRepository();
        }
        return instance;
    }

    public ArrayList<Habit> getList(){
        return list;
    }

    private void initialize(){
        list = new ArrayList<>();
        ArrayList<Category> categories = CategoryRepository.getInstance().getList();
        list.add(new Habit("Habit1", "Habit number 1", "20/11/2022", "", categories.get(0)));
        list.add(new Habit("Habit2", "Habit number 2", "20/11/2022", "", categories.get(1)));
        list.add(new Habit("Habit3", "Habit number 3", "20/11/2022", "", categories.get(2)));
        list.add(new Habit("Habit4", "Habit number 4", "20/11/2022", "", categories.get(2)));
        list.add(new Habit("Habit5", "Habit number 5", "20/11/2022", "", categories.get(1)));
        list.add(new Habit("Habit6", "Habit number 6", "20/11/2022", "", categories.get(0)));
        list.add(new Habit("Habit7", "Habit number 7", "20/11/2022", "", categories.get(0)));
        list.add(new Habit("Habit8", "Habit number 8", "20/11/2022", "", categories.get(1)));
        list.add(new Habit("Habit9", "Habit number 9", "20/11/2022", "", categories.get(2)));
        list.add(new Habit("Habit10", "Habit number 10", "20/11/2022", "", categories.get(2)));
        list.add(new Habit("Habit11", "Habit number 11", "20/11/2022", "", categories.get(1)));
        list.add(new Habit("Habit12", "Habit number 12", "20/11/2022", "", categories.get(0)));
        list.add(new Habit("Habit13", "Habit number 13", "20/11/2022", "", categories.get(0)));
        list.add(new Habit("Habit14", "Habit number 14", "20/11/2022", "", categories.get(1)));
        list.add(new Habit("Habit15", "Habit number 15", "20/11/2022", "", categories.get(2)));
        list.add(new Habit("Habit16", "Habit number 16", "20/11/2022", "", categories.get(2)));

        list.get(0).setCompletedDaysCount(5);
        list.get(0).setCurrentDaysCount(7);


    }

    public boolean addHabit(Habit habit, Category category) {
        boolean succes = false;
        habit.setCategory(category);
        if(!list.contains(habit)){
            list.add(habit);
            succes = true;
        }
        return succes;
    }

    public boolean editHabit(Habit habit, Habit editedHabit, Category category) {
        boolean success = false;
        habit.setCategory(category);
        int index = list.indexOf(editedHabit);
        list.remove(editedHabit);
        if(list.contains(habit)){
            list.add(index, editedHabit);
        }else {
            list.add(index, habit);
            success = true;
        }
        return success;
    }

    public void deleteHabit(Habit habit){
        deletedPosition = list.indexOf(habit);
        list.remove(habit);
    }

    public void undo(Habit habit) {
        list.add(deletedPosition, habit);
    }
}
