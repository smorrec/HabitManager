package com.example.habitmanager.data.category.repository;


import android.util.Log;

import com.example.habitmanager.R;
import com.example.habitmanager.data.category.dao.CategoryDao;
import com.example.habitmanager.data.category.model.Category;
import com.example.habitmanager.data.habit.model.Habit;
import com.example.habitmanager.database.Database;
import com.example.habitmanager.ui.HabitManagerApplication;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class CategoryRepository {
    private CategoryDao categoryDao;
    private static CategoryRepository instance;

    private CategoryRepository(){
        categoryDao = Database.getINSTANCE().categoryDao();
        initialize();
    }

    public static CategoryRepository getInstance(){
        if(instance == null){
            instance = new CategoryRepository();
        }
        return instance;
    }

    private void initialize(){
        HabitManagerApplication.getExecutor().submit(() -> {
            categoryDao.insert(new Category(1, "Actividad Fisica", R.drawable.ic_sport));
        });
        HabitManagerApplication.getExecutor().submit(() -> {
            categoryDao.insert(new Category(2, "Entretenimiento", R.drawable.ic_entertainment));
        });
        HabitManagerApplication.getExecutor().submit(() -> {
            categoryDao.insert(new Category(3, "Idiomas", R.drawable.ic_language));
        });


    }

    public ArrayList<Category> getList() {
        try {
            return HabitManagerApplication.getExecutor().submit(() ->
                    (ArrayList<Category>) categoryDao.selectAll()).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public int getPicture(int id){
        try {
            return HabitManagerApplication.getExecutor().submit(() -> (categoryDao.selectById(id).getPicture())).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String getName(int id){
        try {
            return HabitManagerApplication.getExecutor().submit(() -> (categoryDao.selectById(id).getName())).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
