package com.example.habitmanager.data.category.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.habitmanager.data.category.model.Category;
import java.util.List;

@Dao
public interface CategoryDao {
    @Insert
    long insert(Category category);

    @Update
    int update(Category category);

    @Delete
    void delete(Category category);

    @Query("DELETE FROM category")
    void deleteAll();

    @Query("SELECT * FROM category ORDER BY name ASC")
    List<Category> selectAll();

    @Query("SELECT * FROM category WHERE id=:id")
    Category selectById(int id);
}
