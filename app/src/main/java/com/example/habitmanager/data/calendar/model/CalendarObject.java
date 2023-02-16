package com.example.habitmanager.data.calendar.model;

import android.content.res.Resources;

import com.example.habitmanager.R;
import com.example.habitmanager.ui.HabitManagerApplication;

import java.util.Calendar;
import java.util.Objects;

public class CalendarObject {
    private Calendar calendar;
    private int day;
    private String weekDay;
    private String month;

    public CalendarObject(int day, String weekDay, String month) {
        this.day = day;
        this.weekDay = weekDay;
        this.month = month;
    }

    public CalendarObject(Calendar calendar) {
        this.calendar = calendar;
        this.day = calendar.get(Calendar.DATE);
        this.weekDay = getWeekDayName();
        this.month = getMonthName();
    }

    public Calendar getCalendar(){
        return calendar;
    }

    public void setCalendar(Calendar calendar){
        this.calendar = calendar;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    private String getWeekDayName(){
        String weekDay = "";
        switch (calendar.get(Calendar.DAY_OF_WEEK)){
            case 1 :
                weekDay = HabitManagerApplication.getContext().getResources().getString(R.string.sunday);
                break;
            case 2 :
                weekDay = HabitManagerApplication.getContext().getResources().getString(R.string.monday);
                break;
            case 3 :
                weekDay = HabitManagerApplication.getContext().getResources().getString(R.string.tuesday);
                break;
            case 4 :
                weekDay = HabitManagerApplication.getContext().getResources().getString(R.string.wednesday);
                break;
            case 5 :
                weekDay = HabitManagerApplication.getContext().getResources().getString(R.string.thursday);
                break;
            case 6 :
                weekDay = HabitManagerApplication.getContext().getResources().getString(R.string.friday);
                break;
            case 7 :
                weekDay = HabitManagerApplication.getContext().getResources().getString(R.string.saturday);
        }
        return weekDay;
    }

    private String getMonthName(){
        String month = "";
        switch (calendar.get(Calendar.MONTH)){
            case 0 :
                month = HabitManagerApplication.getContext().getResources().getString(R.string.january);
                break;
            case 1 :
                month = HabitManagerApplication.getContext().getResources().getString(R.string.february);
                break;
            case 2 :
                month = HabitManagerApplication.getContext().getResources().getString(R.string.march);
                break;
            case 3 :
                month = HabitManagerApplication.getContext().getResources().getString(R.string.april);
                break;
            case 4 :
                month = HabitManagerApplication.getContext().getResources().getString(R.string.may);
                break;
            case 5 :
                month = HabitManagerApplication.getContext().getResources().getString(R.string.june);
                break;
            case 6 :
                month = HabitManagerApplication.getContext().getResources().getString(R.string.july);
                break;
            case 7 :
                month = HabitManagerApplication.getContext().getResources().getString(R.string.august);
                break;
            case 8 :
                month = HabitManagerApplication.getContext().getResources().getString(R.string.september);
                break;
            case 9 :
                month = HabitManagerApplication.getContext().getResources().getString(R.string.october);
                break;
            case 10 :
                month = HabitManagerApplication.getContext().getResources().getString(R.string.november);
                break;
            case 11 :
                month = HabitManagerApplication.getContext().getResources().getString(R.string.december);
                break;
        }
        return month;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CalendarObject that = (CalendarObject) o;
        return day == that.day && weekDay.equals(that.weekDay) && month.equals(that.month) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(calendar);
    }

    @Override
    public String toString() {
        return "CalendarObject{" +
                "day=" + day +
                ", weekDay='" + weekDay + '\'' +
                ", month='" + month + '\'' +
                '}';
    }
}
