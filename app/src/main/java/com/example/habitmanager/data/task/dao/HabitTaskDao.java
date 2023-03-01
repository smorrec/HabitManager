package com.example.habitmanager.data.task.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.habitmanager.data.task.model.HabitTask;

import java.util.List;

@Dao
public interface HabitTaskDao {
    @Insert
    long insert(HabitTask habitTask);

    @Update
    void update(HabitTask habitTask);

    @Delete
    void delete(HabitTask habitTask);

    @Query("DELETE FROM habittask")
    void deleteAll();

    @Query("DELETE FROM habittask WHERE habit_Name=:habitname")
    void deleteWhereHabit(String habitname);

    @Query("SELECT * FROM habittask ORDER BY idTask ASC")
    List<HabitTask> selectAll();

    @Query("SELECT * FROM habittask WHERE idTask=:id")
    HabitTask selectById(int id);
}
