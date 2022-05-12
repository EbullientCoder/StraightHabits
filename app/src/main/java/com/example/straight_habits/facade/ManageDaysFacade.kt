package com.example.straight_habits.facade

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.straight_habits.database.RoomDB
import com.example.straight_habits.models.HabitModel
import java.time.LocalDateTime

class ManageDaysFacade {
    companion object{
        //Return Days List
        suspend fun resetPrevDayHabits(day: String, context: Context){
            //Database
            val db = RoomDB.getInstance(context)
            val dao = db.habitDAO()
            //Habits List
            var habitsList: MutableList<HabitModel> = ArrayList()

            //Get the Habits List of the previous day
            when(day){
                "Monday" -> {
                    habitsList = dao.readAll("Sunday")
                    dao.deleteAllFromDay("Sunday")
                }
                "Tuesday" -> {
                    habitsList = dao.readAll("Monday")
                    dao.deleteAllFromDay("Monday")
                }
                "Wednesday" -> {
                    habitsList = dao.readAll("Tuesday")
                    dao.deleteAllFromDay("Tuesday")
                }
                "Thursday" -> {
                    habitsList = dao.readAll("Wednesday")
                    dao.deleteAllFromDay("Wednesday")
                }
                "Friday" -> {
                    habitsList = dao.readAll("Thursday")
                    dao.deleteAllFromDay("Thursday")
                }
                "Saturday" -> {
                    habitsList = dao.readAll("Friday")
                    dao.deleteAllFromDay("Friday")
                }
                "Sunday" -> {
                    habitsList = dao.readAll("Saturday")
                    dao.deleteAllFromDay("Saturday")
                }
            }

            //Reset the List
            ManageHabitsFacade.resetHabitsList(habitsList)

            //Update the new habits list on the DB
            for(habit in habitsList)
                dao.insert(habit)

            //Close the DB Connection
            db.close()
        }



        //Get the Current Date
        @RequiresApi(Build.VERSION_CODES.O)
        fun getCurrentDate(): String{
            val day = LocalDateTime.now().dayOfWeek.toString().lowercase().capitalize()
            val num = LocalDateTime.now().dayOfMonth.toString()
            val month = LocalDateTime.now().month.toString().lowercase().capitalize()

            return "$day, $num $month"
        }

        //Get the Current Day
        @RequiresApi(Build.VERSION_CODES.O)
        fun getCurrentDay(): String{
            return LocalDateTime.now().dayOfWeek.toString().lowercase().capitalize()
        }
    }
}