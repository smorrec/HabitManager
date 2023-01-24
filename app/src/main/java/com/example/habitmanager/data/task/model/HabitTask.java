package com.example.habitmanager.data.task.model;

import com.example.habitmanager.data.calendar.model.CalendarObject;
import com.example.habitmanager.data.habit.model.Habit;

import java.util.Calendar;

public class HabitTask {
    private Habit habit;
    private CalendarObject calendar;
    private boolean completed;

    public HabitTask(Habit habit, CalendarObject calendar) {
        this.habit = habit;
        this.calendar = calendar;
    }

    public Habit getHabit() {
        return habit;
    }

    public void setHabit(Habit habit) {
        this.habit = habit;
    }

    public CalendarObject getCalendar() {
        return calendar;
    }

    public void setCalendar(CalendarObject calendar) {
        this.calendar = calendar;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "HabitTask{" +
                "habit=" + habit +
                ", calendar=" + calendar +
                ", completed=" + completed +
                '}';
    }
}
