package com.example.habitmanager.data.user.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.habitmanager.data.user.model.User;

public class UserViewModel extends ViewModel {
    private MutableLiveData<User> userLogged = new MutableLiveData<>();
}
