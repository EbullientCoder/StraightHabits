package com.example.straight_habits.dao


import androidx.room.*
import com.example.straight_habits.models.RoutineModel

@Dao
interface RoutineDAO {
    //Insert Habit
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(routine : RoutineModel)

    //Delete One Habit
    @Query("DELETE FROM HabitTable WHERE Day = :day AND Name = :name AND Category = :category AND Start = :start AND Information = :info")
    suspend fun delete(day: String, name: String, category: String, start: String, info: String)

    //Delete the Routine of the Category
    @Query("DELETE FROM HabitTable WHERE Category = :category")
    suspend fun deleteRoutineListCategory(category: String)

    //Delete all Habits
    @Query("DELETE FROM HabitTable")
    suspend fun deleteAll()

    //Delete all Habits in the Current Day
    @Query("DELETE FROM HabitTable WHERE Day = :currentDay")
    suspend fun deleteAllFromDay(currentDay: String)

    //Read all Habits
    @Query("SELECT * FROM HabitTable WHERE Day = :currentDay")
    suspend fun readAll(currentDay: String) : MutableList<RoutineModel>

    //Read all Habits from category
    @Query("SELECT * FROM HabitTable WHERE Day = :currentDay AND Category = :category")
    suspend fun readAllCategory(currentDay: String, category: String) : MutableList<RoutineModel>

    //Edit one Habit
    //@Query("UPDATE HabitTable SET WHERE ID = :habitID")
    @Update
    suspend fun edit(routine: RoutineModel)

    //Change Routine Category
    @Query("UPDATE HabitTable SET Category = :newCategory WHERE Category = :oldCategory")
    suspend fun editRoutineCategory(newCategory: String, oldCategory: String)
}