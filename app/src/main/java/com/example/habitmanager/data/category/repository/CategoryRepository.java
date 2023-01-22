package com.example.habitmanager.data.category.repository;


import com.example.habitmanager.R;
import com.example.habitmanager.data.category.model.Category;

import java.util.ArrayList;

public class CategoryRepository {
    private ArrayList<Category> list;
    private static CategoryRepository instance;

    public static CategoryRepository getInstance(){
        if(instance == null){
            instance = new CategoryRepository();
        }
        return instance;
    }

    public ArrayList<Category> getList() {
        list = new ArrayList<>();

        list.add(new Category(0, "Actividad Fisica", R.drawable.ic_sport));
        list.add(new Category(1, "Entretenimiento",R.drawable.ic_entertainment));
        list.add(new Category(2, "Idiomas", R.drawable.ic_language));

        return list;
    }
}
