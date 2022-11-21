package com.example.habitmanager.data;

import com.example.habitmanager.data.model.Category;
import com.example.habitmanager.data.model.Habit;

import java.util.ArrayList;
import java.util.Date;

public class HabitRepository {
    private ArrayList<Habit> list;
    private static HabitRepository instance;

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
        list.add(new Habit("Habit1", "Habit number 1", "20/11/2022", "", Category.PLACEHOLDER));
        list.add(new Habit("Habit2", "Habit number 2", "20/11/2022", "", Category.PLACEHOLDER));
        list.add(new Habit("Habit3", "Habit number 3", "20/11/2022", "", Category.PLACEHOLDER));
        list.add(new Habit("Habit4", "Habit number 4", "20/11/2022", "", Category.PLACEHOLDER));
        list.add(new Habit("Habit5", "Habit number 5", "20/11/2022", "", Category.PLACEHOLDER));
        list.add(new Habit("Habit6", "Habit number 6", "20/11/2022", "", Category.PLACEHOLDER));
        list.add(new Habit("Habit7", "Habit number 3", "20/11/2022", "", Category.PLACEHOLDER));
        list.add(new Habit("Habit8", "Habit number 3", "20/11/2022", "", Category.PLACEHOLDER));
        list.add(new Habit("Habit9", "Habit number 3", "20/11/2022", "", Category.PLACEHOLDER));
        list.add(new Habit("Habit10", "Habit number 3", "20/11/2022", "", Category.PLACEHOLDER));
        list.add(new Habit("Habit11", "Habit number 3", "20/11/2022", "", Category.PLACEHOLDER));
        list.add(new Habit("Habit12", "Habit number 3", "20/11/2022", "", Category.PLACEHOLDER));
        list.add(new Habit("Habit13", "Habit number 3", "20/11/2022", "", Category.PLACEHOLDER));
        list.add(new Habit("Habit14", "Habit number 3", "20/11/2022", "", Category.PLACEHOLDER));
        list.add(new Habit("Habit15", "Habit number 3", "20/11/2022", "", Category.PLACEHOLDER));
        list.add(new Habit("Habit16", "Habit number 3", "20/11/2022", "", Category.PLACEHOLDER));

        list.get(0).setCompletedDaysCount(5);
        list.get(0).setCurrentDaysCount(7);


    }
}
