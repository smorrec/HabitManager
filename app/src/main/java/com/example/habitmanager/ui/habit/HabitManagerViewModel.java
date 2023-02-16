package com.example.habitmanager.ui.habit;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.habitmanager.data.category.model.Category;
import com.example.habitmanager.data.habit.model.Habit;
import com.example.habitmanager.data.habit.repository.HabitRepository;
import com.example.habitmanager.data.task.repository.HabitTaskRepository;

public class HabitManagerViewModel extends ViewModel {
    private MutableLiveData<HabitManagerResult> resultMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<HabitManagerResult> getResultMutableLiveData() {
        return resultMutableLiveData;
    }

    public boolean validateHabitName(Habit habit){
        boolean valido = false;

        if(TextUtils.isEmpty(habit.getName())){
            resultMutableLiveData.setValue(HabitManagerResult.NAMEEMPTY);
        }else {
            valido = true;
        }

        return valido;
    }

    public boolean validateHabitStartDate(Habit habit){
        boolean valido = false;
        if(TextUtils.isEmpty(habit.getStartDateString())){
            resultMutableLiveData.setValue(HabitManagerResult.STARTDATEEMPTY);
        }else {
            valido = true;
        }

        return valido;
    }

    public void addHabit(Habit habit, Category category){
        if(validateHabitName(habit) && validateHabitStartDate(habit)){
            if(HabitRepository.getInstance().addHabit(habit, category)){
                resultMutableLiveData.setValue(HabitManagerResult.SUCCESS);
                HabitTaskRepository.getInstance().addTask(habit);
            }else {
                resultMutableLiveData.setValue(HabitManagerResult.FAILURE);
            }
        }
    }

    public void editHabit(Habit habit, Habit editedHabit, Category category){
        if(validateHabitName(habit) && validateHabitStartDate(habit)) {
            if (HabitRepository.getInstance().editHabit(habit, editedHabit, category)) {
                resultMutableLiveData.setValue(HabitManagerResult.SUCCESS);
            } else {
                resultMutableLiveData.setValue(HabitManagerResult.FAILURE);
            }
        }
    }
}
