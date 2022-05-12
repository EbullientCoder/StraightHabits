package com.example.straight_habits.dao


import androidx.room.*
import com.example.straight_habits.models.HabitModel

@Dao
interface HabitDAO {
    //Insert Habit
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(habit : HabitModel)

    //Delete One Habit
    @Query("DELETE FROM HabitTable WHERE Day = :day AND Name = :name")
    suspend fun delete(day: String, name: String)

    //Delete all Habits
    @Query("DELETE FROM HabitTable")
    suspend fun deleteAll()

    //Delete all Habits in the Current Day
    @Query("DELETE FROM HabitTable WHERE Day = :currentDay")
    suspend fun deleteAllFromDay(currentDay: String)

    //Read all Habits
    @Query("SELECT * FROM HabitTable WHERE Day = :currentDay")
    suspend fun readAll(currentDay: String) : MutableList<HabitModel>

    //Read all Habits from category
    @Query("SELECT * FROM HabitTable WHERE Day = :currentDay AND Category = :category")
    suspend fun readAllCategory(currentDay: String, category: String) : MutableList<HabitModel>

    //Edit one Habit
    //@Query("UPDATE HabitTable SET WHERE ID = :habitID")
    @Update
    suspend fun edit(habit: HabitModel)
}