package com.example.straight_habits.controller.application

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.straight_habits.beans.RoutineBean
import com.example.straight_habits.database.RoomDB
import com.example.straight_habits.facade.ManageDaysFacade
import com.example.straight_habits.models.RoutineModel

class ManageRoutine {
    //Create the Habit Model and save it into the Room DB
    suspend fun addRoutine(routine : RoutineBean, day: String, context: Context){
        //Get the DB Instance
        val db = RoomDB.getInstance(context)
        val dao = db.routineDAO()

        //Add the Habit
        dao.insert(
            RoutineModel(
                0,
                routine.getName(),
                routine.getInformation(),
                routine.getCategory(),
                routine.getStartHour(),
                routine.getEndHour(),
                day,
                routine.getSelected(),
                routine.getDone()
            )
        )

        Log.i(String(), routine.getName() + " Added!")

        //Close the Database
        //db.close()
    }


    //Add the Habits List of the Current Day into the DB
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun addRoutineListCurrentDay(routines : MutableList<RoutineBean>, context: Context){
        //Get the DB Instance
        val db = RoomDB.getInstance(context)
        val dao = db.routineDAO()

        val day = ManageDaysFacade.getCurrentDay()

        //Loop to add the List
        for(habit in routines)
            dao.insert(
                RoutineModel(
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

    //Add the Habit List in the selected day
    suspend fun addRoutineListSelectedDay(routines: MutableList<RoutineBean>, day: String, context: Context){
        //Get the DB Instance
        val db = RoomDB.getInstance(context)
        val dao = db.routineDAO()

        //Loop to add the List
        for(habit in routines)
            dao.insert(
                RoutineModel(
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
    }

    //Add the Habits List of all days inside the DB
    suspend fun addRoutineListAllDay(routines : MutableList<RoutineBean>, context: Context){
        //Get the DB Instance
        val db = RoomDB.getInstance(context)
        val dao = db.routineDAO()

        //Get the Days List
        val days = ManageDaysFacade.getDaysList()

        //Loop to add the List
        for(day in days)
            for(habit in routines)
                dao.insert(
                    RoutineModel(
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
    suspend fun deleteRoutine(routineModel: RoutineModel, context: Context){
        //Get the Database Instance
        val db = RoomDB.getInstance(context)
        val dao = db.routineDAO()

        //Remove
        dao.delete(routineModel.getDay(), routineModel.getName(), routineModel.getCategory(), routineModel.getStartHour(), routineModel.getInformation())

        //Close the Connection
        //db.close()
    }


    //Delete all the Habits
    suspend fun deleteAllRoutine(context: Context){
        //Get the Database Instance
        val db = RoomDB.getInstance(context)
        val dao = db.routineDAO()

        dao.deleteAll()

        //Close the Connection
        //db.close()
    }


    //Delete all the Habits from the Current Day
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun deleteAllRoutineFromDay(context: Context){
        //Get the Database Instance
        val db = RoomDB.getInstance(context)
        val dao = db.routineDAO()

        val day = ManageDaysFacade.getCurrentDay()
        dao.deleteAllFromDay(day)

        //Close the Connection
        //db.close()
    }


    //Edit Habit
    suspend fun editRoutine(routine: RoutineModel, context: Context){
        //Get the Database Instance
        val db = RoomDB.getInstance(context)
        val dao = db.routineDAO()

        dao.edit(routine)
    }
}