package com.example.habitmanager.data.habit.comparator;

import com.example.habitmanager.data.habit.model.Habit;

import java.util.Comparator;

public class HabitComparatorByCategory implements Comparator<Habit> {
    @Override
    public int compare(Habit habit, Habit t1) {
        return habit.getCategory().compareTo(t1.getCategory());
    }
}
