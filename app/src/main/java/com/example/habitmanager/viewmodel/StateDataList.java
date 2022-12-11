package com.example.habitmanager.viewmodel;

public class StateDataList<T> {

    public enum DataState{
        CREATED,
        LOADING,
        NODATA,
        SUCCESS,
        COMPLETED
    }

    private DataState state;
    private T data;

    public StateDataList(){
        this.state = DataState.CREATED;
        this.data = null;
    }

    public StateDataList<T> setLoading(){
        this.state = DataState.LOADING;
        this.data = null;
        return this;
    }

    public StateDataList<T> setNodata(){
        this.state = DataState.NODATA;
        this.data = null;
        return this;
    }

    public StateDataList<T> setSuccess(T data){
        this.state = DataState.SUCCESS;
        this.data = data;
        return this;
    }

    public StateDataList<T> setCompleted(){
        this.state = DataState.COMPLETED;
        return this;
    }

    public DataState getState() {
        return state;
    }

    public T getData() {
        return data;
    }
}
