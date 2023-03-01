package com.example.habitmanager.ui.habit;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.habitmanager.data.habit.model.Habit;
import com.example.habitmanager.data.habit.repository.HabitRepository;
import com.example.habitmanager.data.task.model.HabitTask;
import com.example.habitmanager.data.task.repository.HabitTaskRepository;
import com.example.habitmanager.viewmodel.StateLiveDataList;

import java.util.ArrayList;

public class HabitListViewModel extends ViewModel {
    private StateLiveDataList<ArrayList<Habit>> stateLiveDataList = new StateLiveDataList<>();
    private MutableLiveData<Habit> deletedHabit = new MutableLiveData<>();
    private boolean undoEnabled = true;

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

    public void delete(Habit habit) {
        HabitRepository.getInstance().deleteHabit(habit);
        HabitTaskRepository.getInstance().deleteTask(habit);
        undoEnabled = true;
        deletedHabit.setValue(habit);
    }

    public MutableLiveData<Habit> getDeletedHabit() {
        return deletedHabit;
    }

    public boolean isUndoEnabled() {
        return undoEnabled;
    }

    public void undo() {
        HabitRepository.getInstance().undo(deletedHabit.getValue());
        HabitTaskRepository.getInstance().undo(deletedHabit.getValue());
    }

    public void setUndoEnabled(boolean b) {
        undoEnabled = b;
    }
}
