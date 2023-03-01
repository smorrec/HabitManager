package com.example.habitmanager.data.task.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.habitmanager.data.calendar.model.CalendarObject;
import com.example.habitmanager.data.category.model.Category;
import com.example.habitmanager.data.habit.model.Habit;

import java.util.Calendar;
@Entity(foreignKeys = @ForeignKey(entity = Habit.class, parentColumns = "habitName",
        childColumns = "habit_Name", onDelete = 5))
public class HabitTask {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int idTask;
    @ColumnInfo(name = "habit_Name")
    private String habitName;
    @Embedded
    private CalendarObject calendar;
    private boolean completed;

    public HabitTask(){

    }

    public HabitTask(Habit habit, CalendarObject calendar) {
        this.habitName = habit.getName();
        this.calendar = calendar;
    }

    public int getIdTask() {
        return idTask;
    }

    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }

    public String getHabitName() {
        return habitName;
    }

    public void setHabitName(String habitName) {
        this.habitName = habitName;
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

    public boolean isCurrentDay(){
        return calendar.equals(new CalendarObject(Calendar.getInstance()));
    }

    @Override
    public String toString() {
        return "HabitTask{" +
                "habit=" + habitName +
                ", calendar=" + calendar +
                ", completed=" + completed +
                '}';
    }
}
