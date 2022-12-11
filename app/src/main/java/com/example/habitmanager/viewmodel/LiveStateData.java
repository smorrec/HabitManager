package com.example.habitmanager.viewmodel;

import androidx.lifecycle.MutableLiveData;

public class LiveStateData<T> extends MutableLiveData<StateData<T>> {
    public void setLoading(){
        setValue(new StateData<T>().loading());
    }

    public void setNoData(){
        setValue(new StateData<T>().noData());
    }

    public void setSuccess(T data){
        setValue(new StateData<T>().success(data));
    }
}
