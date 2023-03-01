package com.example.habitmanager.data.habit.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.habitmanager.data.habit.model.Habit;
import java.util.List;

@Dao
public interface HabitDao {
    @Insert
    long insert(Habit habit);

    @Update
    int update(Habit habit);

    @Delete
    void delete(Habit habit);

    @Query("DELETE FROM habit")
    void deleteAll();

    @Query("SELECT * FROM habit ORDER BY habitname ASC")
    List<Habit> selectAll();

    @Query("SELECT * FROM habit WHERE habitname=:habitname")
    Habit selectByName(String habitname);
}
