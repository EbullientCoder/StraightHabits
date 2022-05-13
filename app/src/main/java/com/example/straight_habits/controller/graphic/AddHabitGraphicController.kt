package com.example.straight_habits.controller.graphic

import android.widget.EditText
import com.example.straight_habits.R
import com.example.straight_habits.activity.AddHabitActivity
import com.example.straight_habits.beans.HabitBean

class AddHabitGraphicController(view: AddHabitActivity) {
    private val addHabitInstance = view
    //Text
    /*private val txtHabitName: EditText
    private val txtHabitInfo: EditText
    private val txtHabitDay: EditText
    private val txtHabitStart: EditText
    private val txtHabitEnd: EditText*/



    //First block of code to be executed
    init {
        //Remove the Action Bar
        addHabitInstance.supportActionBar?.hide()

        //Text
        /*txtHabitName = view.findViewById(R.id.txt_habit_name)
        txtHabitInfo = view.findViewById(R.id.txt_habit_info)
        txtHabitDay = view.findViewById(R.id.txt_habit_day)
        txtHabitStart = view.findViewById(R.id.txt_habit_start)
        txtHabitEnd = view.findViewById(R.id.txt_habit_end)*/
    }



    //Return Habit Bean
    fun getHabit(): HabitBean{
        var name = "NULL"
        var info = "NULL"
        var category = "Morning"
        var start = "NULL"
        var end = "NULL"

        //Name
        /*if (txtHabitName.editableText.toString() != null)
            name = txtHabitName.editableText.toString()
        //info
        if (txtHabitInfo.editableText.toString() != null)
            info = txtHabitInfo.editableText.toString()
        //Start
        if (txtHabitStart.editableText.toString() != null)
            start = txtHabitStart.editableText.toString()
        //End
        if (txtHabitEnd.editableText.toString() != null)
            end = txtHabitEnd.editableText.toString()*/

        //Return the Habit Bean
        return HabitBean(0, name, info,category, start, end, false, false)
    }


    //Get Day
    fun getDay(): String{
        var day = "NULL"

        //Day
        //if (txtHabitDay.editableText.toString() != null)
        //    day = txtHabitDay.editableText.toString()

        return day
    }
}