package com.example.straight_habits.controller.application

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.straight_habits.beans.HabitBean
import com.example.straight_habits.database.RoomDB
import com.example.straight_habits.facade.ManageDaysFacade
import com.example.straight_habits.models.HabitModel

class ManageHabits {
    //Create the Habit Model and save it into the Room DB
    suspend fun addHabit(habit : HabitBean, day : String, context: Context){
        //Get the DB Instance
        val db = RoomDB.getInstance(context)
        val dao = db.habitDAO()

        //Add the Habit
        dao.insert(
            HabitModel(
                0,
                habit.getName(),
                habit.getInformation(),
                habit.getCategory(),
                habit.getStartHour(),
                habit.getEndHour(),
                day,
                habit.getSelected(),
                habit.getDone()
            )
        )

        Log.i(String(), habit.getName() + " Added!")

        //Close the Database
        //db.close()
    }


    //Add the Habits List of the Current Day into the DB
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun addHabitsListCurrentDay(habits : MutableList<HabitBean>, context: Context){
        //Get the DB Instance
        val db = RoomDB.getInstance(context)
        val dao = db.habitDAO()

        val day = ManageDaysFacade.getCurrentDay()

        //Loop to add the List
        for(habit in habits)
            dao.insert(
                HabitModel(
                    0,
                    habit.getName(),
                    habit.getInformation(),
                    habit.getCategory(),
                    habit.getStartHour(),
                    habit.getEndHour(),
                    day,
                    habit.getSelected(),
                    habit.getDone()
                )
            )

        //Close the Database
        //db.close()
    }

    //Add the Habits List of all days inside the DB
    suspend fun addHabitsListAllDay(habits : MutableList<HabitBean>, context: Context){
        //Get the DB Instance
        val db = RoomDB.getInstance(context)
        val dao = db.habitDAO()

        //Get the Days List
        val days = ManageDaysFacade.getDaysList()

        //Loop to add the List
        for(day in days)
            for(habit in habits)
                dao.insert(
                    HabitModel(
                        0,
                        habit.getName(),
                        habit.getInformation(),
                        habit.getCategory(),
                        habit.getStartHour(),
                        habit.getEndHour(),
                        day,
                        habit.getSelected(),
                        habit.getDone()
                    )
                )

        //Close the Database
        //db.close()
    }





    //Delete the Passed Habit
    suspend fun deleteHabit(habitModel: HabitModel, context: Context){
        //Get the Database Instance
        val db = RoomDB.getInstance(context)
        val dao = db.habitDAO()

        //Remove
        dao.delete(habitModel.getDay(), habitModel.getName(), habitModel.getCategory(), habitModel.getStartHour(), habitModel.getInformation())

        //Close the Connection
        //db.close()
    }


    //Delete all the Habits
    suspend fun deleteAllHabits(context: Context){
        //Get the Database Instance
        val db = RoomDB.getInstance(context)
        val dao = db.habitDAO()

        dao.deleteAll()

        //Close the Connection
        //db.close()
    }


    //Delete all the Habits from the Current Day
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun deleteAllHabitsFromDay(context: Context){
        //Get the Database Instance
        val db = RoomDB.getInstance(context)
        val dao = db.habitDAO()

        val day = ManageDaysFacade.getCurrentDay()
        dao.deleteAllFromDay(day)

        //Close the Connection
        //db.close()
    }


    //Edit Habit
    suspend fun editHabit(habit: HabitModel, context: Context){
        //Get the Database Instance
        val db = RoomDB.getInstance(context)
        val dao = db.habitDAO()

        dao.edit(habit)
    }
}