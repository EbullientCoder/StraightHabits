package com.example.straight_habits.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.straight_habits.dao.CategoryDAO
import com.example.straight_habits.dao.RoutineDAO
import com.example.straight_habits.models.CategoryModel
import com.example.straight_habits.models.RoutineModel

//Add Database Entities
@Database(entities = [RoutineModel::class, CategoryModel::class], version = 1, exportSchema = false )
abstract class RoomDB : RoomDatabase(){

    //Singleton to get the Instance
    companion object{
        //Database Instance
        @Volatile
        private var INSTANCE : RoomDB? = null

        //Get the Instance Method
        fun getInstance(context : Context) : RoomDB{
            val tempInstance = INSTANCE
            if(tempInstance != null) return tempInstance

            //Manage the Multi Threads Access
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDB::class.java,
                    "habits_database").build()

                INSTANCE = instance

                return instance
            }
        }
    }


    //DAOs
    abstract fun routineDAO(): RoutineDAO
    abstract fun categoryDAO(): CategoryDAO
}