package com.example.habitmanager.ui.habit;

import androidx.lifecycle.ViewModel;

import com.example.habitmanager.data.HabitRepository;
import com.example.habitmanager.data.model.Habit;
import com.example.habitmanager.viewmodel.LiveStateData;

import java.util.ArrayList;

public class HabitViewModel extends ViewModel {
    private LiveStateData<ArrayList<Habit>> listLiveStateData = new LiveStateData<>();

    public LiveStateData<ArrayList<Habit>> getListLiveStateData() {
        return listLiveStateData;
    }

    public void getDataList(){
        listLiveStateData.setLoading();

        ArrayList<Habit> list = HabitRepository.getInstance().getList();
        if (list.isEmpty()){
            listLiveStateData.setNoData();
        }else {
            listLiveStateData.setSuccess(list);
        }
    }
}
