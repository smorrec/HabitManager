package com.example.habitmanager.ui.habit;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.habitmanager.data.model.Habit;
import com.example.habitmanager.data.repository.HabitRepository;
import com.example.habitmanager.viewmodel.StateLiveDataList;

import java.util.ArrayList;

public class HabitListViewModel extends ViewModel {
    private StateLiveDataList<ArrayList<Habit>> stateLiveDataList = new StateLiveDataList<>();
    private MutableLiveData<Habit> deletedHabit = new MutableLiveData<>();

    public StateLiveDataList<ArrayList<Habit>> getStateLiveDataList() {
        return stateLiveDataList;
    }

    public void getDataList(){
        stateLiveDataList.setLoading();

        ArrayList<Habit> list = HabitRepository.getInstance().getList();
        if(list.isEmpty()){
            stateLiveDataList.setNoData();
        }else {
            stateLiveDataList.setSuccess(list);
        }
    }

    public void delete(int position) {
        Habit habit = HabitRepository.getInstance().getList().get(position);
        HabitRepository.getInstance().deleteHabit(habit);
        deletedHabit.setValue(habit);
    }
}
