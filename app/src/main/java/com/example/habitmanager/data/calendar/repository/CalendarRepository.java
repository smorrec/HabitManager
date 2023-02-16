package com.example.habitmanager.data.calendar.repository;

import android.content.res.Resources;
import android.util.Log;

import com.example.habitmanager.R;
import com.example.habitmanager.data.calendar.model.CalendarObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CalendarRepository {
    private ArrayList<CalendarObject> list;
    private static CalendarRepository instance;

    private CalendarRepository(){
        inicialize();
    }

    public static CalendarRepository getInstance(){
        if(instance == null){
            instance = new CalendarRepository();
        }
        return instance;
    }

    public ArrayList<CalendarObject> getList(){
        return list;
    }

    private void inicialize(){
        list = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        Date curDate = new Date();
        calendar.setTime(curDate);
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(calendar.getTimeInMillis());
        list.add(new CalendarObject(c));
        for(int i = 0; i < 59; i++){
            calendar.add(Calendar.DATE, 1);
            c = Calendar.getInstance();
            c.setTimeInMillis(calendar.getTimeInMillis());
            Log.d("Calendar", c.toString());
            list.add(new CalendarObject(c));
        }
    }

}
