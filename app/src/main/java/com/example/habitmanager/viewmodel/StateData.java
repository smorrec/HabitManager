package com.example.habitmanager.viewmodel;

public class StateData<T> {
    public enum DataState{
        CREATED, LOADING, NODATA, SUCCESS, COMPLETED
    }

    private DataState dataState;
    private T data;

    public StateData() {
        this.dataState = DataState.CREATED;
        data = null;
    }

    public DataState getDataState() {
        return dataState;
    }

    public T getData() {
        return data;
    }

    public StateData<T> loading(){
        dataState = DataState.LOADING;
        data = null;
        return this;
    }

    public StateData<T> noData(){
        dataState = DataState.NODATA;
        data = null;
        return this;
    }

    public StateData<T> success(T data){
        dataState = DataState.SUCCESS;
        this.data = data;
        return this;
    }

    public StateData<T> completed(){
        dataState = DataState.COMPLETED;
        return this;
    }
}
