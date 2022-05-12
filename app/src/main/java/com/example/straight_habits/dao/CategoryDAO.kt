package com.example.straight_habits.dao

import androidx.room.*
import com.example.straight_habits.models.CategoryModel


@Dao
interface CategoryDAO {
    //Insert Category
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(category: CategoryModel)

    //Delete One Category
    @Query("DELETE FROM CategoryTable WHERE Name = :name")
    suspend fun delete(name: String)

    //Delete all Categories
    @Query("DELETE FROM CategoryTable")
    suspend fun deleteAll()

    //Read all Categories
    @Query("SELECT * FROM CategoryTable")
    suspend fun readAll() : MutableList<CategoryModel>

    //Edit one Habit
    @Update
    suspend fun edit(category: CategoryModel)
}