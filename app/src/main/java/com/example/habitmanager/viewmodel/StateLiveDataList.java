package com.example.habitmanager.viewmodel;

import androidx.lifecycle.MutableLiveData;

public class StateLiveDataList<T> extends MutableLiveData<StateDataList<T>> {
    public void setLoading(){
        setValue(new StateDataList<T>().setLoading());
    }

    public void setNoData(){
        setValue(new StateDataList<T>().setNodata());
    }

    public void setSuccess(T data){
        setValue(new StateDataList<T>().setSuccess(data));
    }

}
