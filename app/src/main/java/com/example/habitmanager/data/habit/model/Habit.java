package com.example.habitmanager.data.habit.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.habitmanager.data.calendar.model.CalendarObject;
import com.example.habitmanager.data.category.model.Category;

import java.util.Calendar;
import java.util.Objects;
@Entity(foreignKeys = @ForeignKey(entity = Category.class, parentColumns = "id",
        childColumns = "category_id", onDelete = 5))
public class Habit implements Parcelable , Comparable<Habit>{
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "habitName")
    private String name;
    private String description;
    @NonNull
    private Calendar startDate;
    private String startDateString;
    private Calendar endDate;
    private String endDateString;

    @ColumnInfo(name = "category_id")
    private int categoryId;
    private int currentDaysCount;
    private int completedDaysCount;

    private boolean finished;

    public static final String KEY = "habit";

    public Habit() {
        currentDaysCount = 0;
        completedDaysCount = 0;
    }

    public Habit(String name, String description, Calendar startDate, Calendar endDate, int categoryId) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.categoryId = categoryId;
        this.currentDaysCount = 0;
        this.completedDaysCount = 0;
    }

    public Habit(Habit habit){
        this.name = habit.getName();
        this.description = habit.getDescription();
        this.startDate = habit.getStartDate();
        this.startDateString = habit.getStartDateString();
        this.endDate = habit.getEndDate();
        this.endDateString = habit.getEndDateString();
        this.categoryId = habit.getCategoryId();
        this.currentDaysCount = habit.getCurrentDaysCount();
        this.completedDaysCount = habit.getCompletedDaysCount();
    }

    public Habit clone(){
        return new Habit(this);
    }

    protected Habit(Parcel in) {
        name = in.readString();
        description = in.readString();
        currentDaysCount = in.readInt();
        completedDaysCount = in.readInt();
    }

    public static final Creator<Habit> CREATOR = new Creator<Habit>() {
        @Override
        public Habit createFromParcel(Parcel in) {
            return new Habit(in);
        }

        @Override
        public Habit[] newArray(int size) {
            return new Habit[size];
        }
    };

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public String getStartDateString(){
        return startDateString;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public void setStartDateString(String startDateString){
        this.startDateString = startDateString;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public String getEndDateString(){
        return endDateString;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public void setEndDateString(String endDateString){
        this.endDateString = endDateString;
    }

    public int getCurrentDaysCount() {
        return currentDaysCount;
    }

    public void setCurrentDaysCount(int currentDaysCount) {
        this.currentDaysCount = currentDaysCount;
    }

    public int getCompletedDaysCount() {
        return completedDaysCount;
    }

    public void setCompletedDaysCount(int completedDaysCount) {
        this.completedDaysCount = completedDaysCount;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public void increaseCurrentDaysCount(){
        this.currentDaysCount++;
    }

    public void increaseCompletedDaysCount(){
        this.completedDaysCount++;
    }

    public void decreaseCompletedDaysCount(){
        this.completedDaysCount--;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Habit habit = (Habit) o;
        return Objects.equals(name, habit.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, startDate, endDate, categoryId, currentDaysCount, completedDaysCount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeInt(currentDaysCount);
        parcel.writeInt(completedDaysCount);
    }

    @Override
    public int compareTo(Habit habit) {
        return this.getName().compareToIgnoreCase(habit.getName());
    }

    public boolean hasTask(CalendarObject calendarObject) {
        Log.d("startDate", String.valueOf(startDate.getTimeInMillis()));
        Log.d("calendarObject", String.valueOf(calendarObject.getCalendar().getTimeInMillis()));
        Log.d("has task", String.valueOf(startDate.getTimeInMillis() <= calendarObject.getCalendar().getTimeInMillis()));
        return startDate.getTimeInMillis() <= calendarObject.getCalendar().getTimeInMillis();
    }
}
