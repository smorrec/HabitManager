package com.example.habitmanager.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.Objects;

public class Habit implements Parcelable {
    public static final String KEY = "habit";
    private String name;
    private String description;
    private String startDate;
    private String endDate;
    private Category category;
    private int currentDaysCount;
    private int completedDaysCount;

    public Habit() {
        currentDaysCount = 0;
        completedDaysCount = 0;
    }

    public Habit(String name, String description, String startDate, String endDate, Category category) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.category = category;
        this.currentDaysCount = 0;
        this.completedDaysCount = 0;
    }

    public Habit(Habit habit){
        this.name = habit.getName();
        this.description = habit.getDescription();
        this.startDate = habit.getStartDate();
        this.endDate = habit.getEndDate();
        this.category = habit.getCategory();
        this.currentDaysCount = habit.getCurrentDaysCount();
        this.completedDaysCount = habit.getCompletedDaysCount();
    }

    public Habit clone(){
        return new Habit(this);
    }

    protected Habit(Parcel in) {
        name = in.readString();
        description = in.readString();
        startDate = in.readString();
        endDate = in.readString();
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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
        return Objects.hash(name, description, startDate, endDate, category, currentDaysCount, completedDaysCount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(startDate);
        parcel.writeString(endDate);
        parcel.writeInt(currentDaysCount);
        parcel.writeInt(completedDaysCount);
    }
}
