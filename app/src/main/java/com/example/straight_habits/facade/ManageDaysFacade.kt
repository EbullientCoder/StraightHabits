package com.example.straight_habits.facade

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.straight_habits.database.RoomDB
import com.example.straight_habits.models.RoutineModel
import java.time.LocalDateTime

class ManageDaysFacade {
    companion object{
        //Reset previous day list
        suspend fun resetPrevDayHabits(day: String, context: Context){
            //Database
            val db = RoomDB.getInstance(context)
            val dao = db.routineDAO()
            //Habits List
            var habitsList: MutableList<RoutineModel> = ArrayList()

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
            ManageRoutineFacade.resetHabitsList(habitsList)

            //Update the new habits list on the DB
            for(habit in habitsList)
                dao.insert(habit)

            //Close the DB Connection
            db.close()
        }

        //Reset passed day list
        suspend fun resetPassedDayRoutine(day: String, context: Context){
            //Database
            val db = RoomDB.getInstance(context)
            val dao = db.routineDAO()
            //Habits List
            var habitsList: MutableList<RoutineModel>

            //Get the Routine List of the passed day
            habitsList = dao.readAll(day)
            //Delete all the Routine List of that day from the DB
            dao.deleteAllFromDay(day)
            //Reset the List
            ManageRoutineFacade.resetHabitsList(habitsList)
            //Update the new habits list on the DB
            for(habit in habitsList) dao.insert(habit)

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
            //return "Friday, 11 April"
        }

        //Get the Current Day
        @RequiresApi(Build.VERSION_CODES.O)
        fun getCurrentDay(): String{
            return LocalDateTime.now().dayOfWeek.toString().lowercase().capitalize()
            //return "Sunday"
        }



        //Get Days List
        fun getDaysList(): MutableList<String>{
            var days: MutableList<String> = ArrayList()
            days.add("Monday")
            days.add("Tuesday")
            days.add("Wednesday")
            days.add("Thursday")
            days.add("Friday")
            days.add("Saturday")
            days.add("Sunday")

            return days
        }
    }
}