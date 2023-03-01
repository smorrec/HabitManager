package com.example.habitmanager.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.habitmanager.data.category.dao.CategoryDao;
import com.example.habitmanager.data.category.model.Category;
import com.example.habitmanager.data.habit.dao.HabitDao;
import com.example.habitmanager.data.habit.model.Habit;
import com.example.habitmanager.data.task.dao.HabitTaskDao;
import com.example.habitmanager.data.task.model.HabitTask;
import com.example.habitmanager.utils.Converters;

@androidx.room.Database(entities = {Habit.class, Category.class, HabitTask.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class Database extends RoomDatabase {

    public abstract HabitDao habitDao();
    public abstract HabitTaskDao habitTaskDao();
    public abstract CategoryDao categoryDao();

    private static volatile Database INSTANCE;

    public static Database getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (Database.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    Database.class, "habitmanager")
                            .fallbackToDestructiveMigration()
                            .build();
                    INSTANCE.getOpenHelper().getReadableDatabase();
                }
            }
        }
        return INSTANCE;
    }

    public static Database getINSTANCE() {
        return INSTANCE;
    }
}
