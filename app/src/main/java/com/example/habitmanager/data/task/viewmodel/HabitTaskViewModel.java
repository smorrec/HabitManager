package com.example.habitmanager.data.task.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.habitmanager.data.task.model.HabitTask;
import com.example.habitmanager.data.task.repository.HabitTaskRepository;

import java.util.ArrayList;

public class HabitTaskViewModel extends ViewModel {
    private MutableLiveData<State> stateMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<State> getStateMutableLiveData() {
        return stateMutableLiveData;
    }

    public void getDataList(){
        ArrayList<HabitTask> list = HabitTaskRepository.getInstance().getList();

        if(list == null){
            stateMutableLiveData.setValue(State.EMPTY);
        }else {
            stateMutableLiveData.setValue(State.FILL);
        }
    }
}
