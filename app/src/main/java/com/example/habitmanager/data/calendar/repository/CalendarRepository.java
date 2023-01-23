package com.example.habitmanager.data.calendar.repository;

import android.content.res.Resources;

import com.example.habitmanager.R;
import com.example.habitmanager.data.calendar.model.CalendarObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CalendarRepository {
    private ArrayList<CalendarObject> list;
    private static CalendarRepository instance;

    public static CalendarRepository getInstance(){
        if(instance == null){
            instance = new CalendarRepository();
        }
        return instance;
    }

    public ArrayList<CalendarObject> getList(){
        list = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        Date curDate = new Date();
        calendar.setTime(curDate);
        list.add(new CalendarObject(calendar));
        for(int i = 0; i < 69; i++){
            calendar.add(Calendar.DATE, 1);
            list.add(new CalendarObject(calendar));
        }
        return list;
    }

}
